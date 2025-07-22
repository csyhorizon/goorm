pipeline {
    agent any

    environment {
        // MySQL [O]
        DB_HOST = 'mysql-db'
        DB_PORT = '3306'
        DB_NAME = 'seot'
        DB_USER = credentials('mysql-username')
        DB_PASSWORD = credentials('mysql-password')

        // Redis [O]
        REDIS_HOST = 'redis-cache'
        REDIS_PORT = '6379'
        REDIS_PASSWORD = credentials('redis-password')

        // MongoDB [O]
        MONGO_USER = credentials('mongo-username')
        MONGO_PASSWORD = credentials('mongo-password')
        MONGO_HOST = 'mongodb'
        MONGO_PORT = '27017'
        MONGO_DB = 'seot'

        // AWS S3 [O]
        AWS_ACCESS_KEY_ID = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
        S3_BUCKET_NAME = 'seotbucket'

        // JWT [O]
        JWT_SECRET_CREDENTIAL_ID = 'jwt-secret-text'

        // Discord [O]
        DISCORD_WEBHOOK_URL_CREDENTIAL_ID = 'DISCORD_WEBHOOK_URL'

        // Github [O]
        GITHUB_REPO_URL_CREDENTIAL_ID = 'github-repo-url'
        GITHUB_BRANCH_CREDENTIAL_ID = 'github-branch'

        // GCP [O]
        GCP_SSH_CREDENTIAL_ID = 'gcp-ssh-key-credential'
        GCP_VM_USER = credentials('host-vm-user')
        GCP_VM_HOST = credentials('host-vm-ip-address-or-hostname')

        // Docker [O]
        DOCKERHUB_CREDENTIAL_ID = 'dockerhub-credential'
        DOCKERHUB_USERNAME = 'chinoel'
        DOCKER_IMAGE_NAME = "${env.DOCKERHUB_USERNAME}/seot-backend"
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    tools {
        jdk 'JDK17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'mysql-username', variable: 'DB_USER'),
                        string(credentialsId: 'mysql-password', variable: 'DB_PASSWORD'),

                        string(credentialsId: 'redis-password', variable: 'REDIS_PASSWORD'),

                        string(credentialsId: 'mongo-username', variable: 'MONGO_USER'),
                        string(credentialsId: 'mongo-password', variable: 'MONGO_PASSWORD'),

                        string(credentialsId: 'jwt-secret-text', variable: 'JWT_SECRET')
                    ]) {
                        sh '''
                            export DB_USER
                            export DB_PASSWORD
                            export REDIS_PASSWORD
                            export MONGO_USER
                            export MONGO_PASSWORD
                            export JWT_SECRET
                            ./gradlew clean build --no-daemon
                        '''
                    }
                }
            }
            post {
                always {
                    junit '**/build/test-results/test/*.xml'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                    archiveArtifacts artifacts: 'build/reports/tests/test/index.html', allowEmptyArchive: true
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."

                    withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIAL_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u '${DOCKER_USER}' -p '${DOCKER_PASSWORD}'"
                        sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Deploy to VM') {
            steps {
                sshagent(credentials: [env.GCP_SSH_CREDENTIAL_ID]) {
                    withCredentials([
                        credentials(credentialsId: 'host-vm-user', variable: 'GCP_VM_USER'),
                        credentials(credentialsId: 'host-vm-ip-address-or-hostname', variable: 'GCP_VM_HOST'),
                        string(credentialsId: 'mysql-username', variable: 'MYSQL_USER'),
                        string(credentialsId: 'mysql-password', variable: 'MYSQL_PASSWORD'),
                        string(credentialsId: 'redis-password', variable: 'REDIS_PASSWORD'),
                        string(credentialsId: 'mongo-username', variable: 'MONGO_USER'),
                        string(credentialsId: 'mongo-password', variable: 'MONGO_PASSWORD'),
                        string(credentialsId: 'jwt-secret-text', variable: 'JWT_SECRET'),
                        string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY'),
                        string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_KEY')
                    ]) {
                        sh '''
                        ssh-keygen -R ${GCP_VM_HOST}

ssh -o StrictHostKeyChecking=no ${GCP_VM_USER}@${GCP_VM_HOST} << EOF
docker pull ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
docker stop seot-backend || true
docker rm seot-backend || true
docker run -d \\
    -p 8080:8080 \\
    --name seot-backend \\
    --network seot \\
    -e SPRING_DATASOURCE_USERNAME=${MYSQL_USER} \\
    -e SPRING_DATASOURCE_PASSWORD=${MYSQL_PASSWORD} \\
    -e SPRING_DATASOURCE_URL="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}" \\
    -e SPRING_REDIS_HOST=${REDIS_HOST} \\
    -e SPRING_REDIS_PORT=${REDIS_PORT} \\
    -e SPRING_REDIS_PASSWORD=${REDIS_PASSWORD} \\
    -e SPRING_DATA_MONGODB_USERNAME=${MONGO_USER} \\
    -e SPRING_DATA_MONGODB_PASSWORD=${MONGO_PASSWORD} \\
    -e SPRING_DATA_MONGODB_HOST=${MONGO_HOST} \\
    -e SPRING_DATA_MONGODB_PORT=${MONGO_PORT} \\
    -e SPRING_DATA_MONGODB_DATABASE=${MONGO_DB} \\
    -e JWT_SECRET=${JWT_SECRET} \\
    -e CLOUD_AWS_CREDENTIALS_ACCESS-KEY=${AWS_ACCESS_KEY} \\
    -e CLOUD_AWS_CREDENTIALS_SECRET-KEY=${AWS_SECRET_KEY} \\
    -e CLOUD_AWS_S3_BUCKET=${S3_BUCKET_NAME} \\
    -e CLOUD_AWS_REGION_STATIC=ap-northeast-2 \\
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
            script {
                echo "Pipeline finished. Status: ${currentBuild.result}"

                withCredentials([string(credentialsId: env.DISCORD_WEBHOOK_URL_CREDENTIAL_ID, variable: 'DISCORD_WEBHOOK_URL')]) {
                    def statusEmoji = (currentBuild.result == 'SUCCESS') ? ':white_check_mark:' : ':x:'
                    def statusColor = (currentBuild.result == 'SUCCESS') ? 65280 : 16711680
                    def nowIso = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone('UTC'))

                    sh """
                    curl -H "Content-Type: application/json" -X POST -d '{
                        "username": "Jenkins CI/CD",
                        "avatar_url": "https://raw.githubusercontent.com/jenkinsci/jenkins/master/core/src/main/resources/jenkins-icon.png",
                        "embeds": [
                            {
                                "title": "${statusEmoji} 빌드 ${currentBuild.result}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                                "description": "프로젝트: **${env.JOB_NAME}**\\n빌드 번호: **${env.BUILD_NUMBER}**\\n상태: **${currentBuild.result}**\\n자세히 보기: ${env.BUILD_URL}",
                                "color": ${statusColor},
                                "timestamp": "${nowIso}"
                            }
                        ]
                    }' ${DISCORD_WEBHOOK_URL}
                    """
                }
            }
            cleanWs()
        }
    }
}