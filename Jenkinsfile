pipeline {
    agent any

    environment {
        GCP_SSH_CREDENTIAL_ID = 'gcp-ssh-key-credential'
        JWT_SECRET_CREDENTIAL_ID = 'jwt-secret-text'
        DISCORD_WEBHOOK_URL_CREDENTIAL_ID = 'DISCORD_WEBHOOK_URL'
        SPRING_BOOT_API_URL_CREDENTIAL_ID = 'spring-boot-api-url'
        KAKAO_MAP_APP_KEY_CREDENTIAL_ID = 'kakao-map-app-key'

        GITHUB_REPO_URL_CREDENTIAL_ID = 'github-repo-url'
        GITHUB_BRANCH_CREDENTIAL_ID = 'github-branch'

        GCP_VM_USER = credentials('host-vm-user')
        GCP_VM_HOST = credentials('host-vm-ip-address-or-hostname')

        DOCKER_IMAGE_NAME = 'seot-frontend'
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Clone Repository') {
            steps {
                withCredentials([
                string(credentialsId: env.GITHUB_REPO_URL_CREDENTIAL_ID, variable: 'REPO_URL'),
                string(credentialsId: env.GITHUB_BRANCH_CREDENTIAL_ID, variable: 'REPO_BRANCH')
            ]) {
                    git branch: "${REPO_BRANCH}", url: "${REPO_URL}"
            }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Deploy to VM') {
            steps {
                sshagent(credentials: [env.GCP_SSH_CREDENTIAL_ID]) {
                    withCredentials([
                    string(credentialsId: env.JWT_SECRET_CREDENTIAL_ID, variable: 'JWT_SECRET'),
                    string(credentialsId: env.SPRING_BOOT_API_URL_CREDENTIAL_ID, variable: 'NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL'),
                // string(credentialsId: env.KAKAO_MAP_APP_KEY_CREDENTIAL_ID, variable: 'NEXT_PUBLIC_KAKAO_MAP_APP_KEY')
                ]) {
                        sh '''
                        ssh-keygen -R ${GCP_VM_HOST}

                        ssh -o StrictHostKeyChecking=no ${GCP_VM_USER}@${GCP_VM_HOST} << EOF
                            docker stop ${DOCKER_IMAGE_NAME} || true
                            docker rm ${DOCKER_IMAGE_NAME} || true

                            docker run -d \\
                                -p 3000:3000 \\
                                --name ${DOCKER_IMAGE_NAME} \\
                                --network seot \\
                                -e "JWT_SECRET=${JWT_SECRET}" \\
                                -e "NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL=${NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL}" \\
                                ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}

                            docker image prune -f
                        EOF
                    '''
                }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished. Status: ${currentBuild.result}"

            withCredentials([string(credentialsId: env.DISCORD_WEBHOOK_URL_CREDENTIAL_ID, variable: 'DISCORD_WEBHOOK_URL')]) {
                script {
                    def statusEmoji = (currentBuild.result == 'SUCCESS') ? ':white_check_mark:' : ':x:'
                    def statusColor = (currentBuild.result == 'SUCCESS') ? 65280 : 16711680

                    sh """
                    curl -H "Content-Type: application/json" -X POST -d '{
                        "username": "Jenkins CI/CD",
                        "avatar_url": "https://raw.githubusercontent.com/jenkinsci/jenkins/master/core/src/main/resources/jenkins-icon.png",
                        "embeds": [
                            {
                                "title": "${statusEmoji} 빌드 ${currentBuild.result}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                                "description": "프로젝트: **${env.JOB_NAME}**\\n빌드 번호: **${env.BUILD_NUMBER}**\\n상태: **${currentBuild.result}**\\n트리거: ${currentBuild.cause}\\n자세히 보기: ${env.BUILD_URL}",
                                "color": ${statusColor},
                                "timestamp": "${new Date().toISOString()}"
                            }
                        ]
                    }' ${DISCORD_WEBHOOK_URL}
                """
                    }
                }
            }
        }
    }
