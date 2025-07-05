pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "gcr.io/${env.GOOGLE_CLOUD_PROJECT_ID ?: '<your-gcp-project-id>'}"
        DOCKER_IMAGE_NAME = "c1one-app"
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"

        DEPLOY_SERVER_USER = "${env.DEPLOY_SERVER_USER}"
        DEPLOY_SERVER_IP = "${env.DEPLOY_SERVER_IP}"
        DEPLOY_PATH = "${env.DEPLOY_PATH}"
        APP_PORT = "${env.APP_PORT}"
        APP_CONTAINER_NAME = "${env.APP_CONTAINER_NAME}"

        DB_URL = "${env.DB_URL}"
        DB_USERNAME = "${env.DB_USERNAME}"
        JWT_EXPIRATION_MS = "${env.JWT_EXPIRATION_MS}"
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
                        string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD_ENV'),
                        string(credentialsId: 'JWT_SECRET_KEY', variable: 'JWT_SECRET_ENV')
                    ]) {
                        if (isUnix()) {
                            sh "DB_URL=\"${env.DB_URL}\" " +
                               "DB_USERNAME=\"${env.DB_USERNAME}\" " +
                               "DB_PASSWORD=\"${DB_PASSWORD_ENV}\" " +
                               "JWT_SECRET=\"${JWT_SECRET_ENV}\" " +
                               "JWT_EXPIRATION_MS=\"${env.JWT_EXPIRATION_MS}\" " +
                               "./gradlew clean build --no-daemon"
                        } else {
                            bat "set DB_URL=\"%DB_URL%\" && set DB_USERNAME=\"%DB_USERNAME%\" && ^" +
                                "set DB_PASSWORD=\"%DB_PASSWORD_ENV%\" && set JWT_SECRET=\"%JWT_SECRET_ENV%\" && ^" +
                                "set JWT_EXPIRATION_MS=\"%JWT_EXPIRATION_MS%\" && ^" +
                                "gradlew.bat clean build"
                        }
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

        stage('Docker Build & Push') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                    sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"

                    sh 'gcloud auth configure-docker'
                    sh "docker push ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    echo "Deploying Docker image ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} to VM"

                    withCredentials([
                        string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD_ENV'),
                        string(credentialsId: 'JWT_SECRET_KEY', variable: 'JWT_SECRET_ENV'),
                        sshUserPrivateKey(credentialsId: 'my-gcp-ssh-key', keyFileVariable: 'SSH_KEY_PATH')
                    ]) {
                        sh """
                        ssh -i ${SSH_KEY_PATH} ${env.DEPLOY_SERVER_USER}@${env.DEPLOY_SERVER_IP} << 'EOF'
                            # 원격 서버에서 실행될 스크립트 시작

                            # 기존 컨테이너 중지 및 삭제
                            docker stop ${env.APP_CONTAINER_NAME} || true
                            docker rm ${env.APP_CONTAINER_NAME} || true

                            # 최신 이미지 pull
                            docker pull ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}

                            # 새로운 컨테이너 실행 (3. 가독성 개선)
                            docker run -d \\
                               -p ${env.APP_PORT}:${env.APP_PORT} \\
                               --name ${env.APP_CONTAINER_NAME} \\
                               -e DB_URL="${env.DB_URL}" \\
                               -e DB_USERNAME="${env.DB_USERNAME}" \\
                               -e DB_PASSWORD="${DB_PASSWORD_ENV}" \\
                               -e JWT_SECRET="${JWT_SECRET_ENV}" \\
                               -e JWT_EXPIRATION_MS="${env.JWT_EXPIRATION_MS}" \\
                               ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}

                            echo "Deployment script finished."
                        EOF
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
            cleanWs()
        }
        success {
            echo 'Build and Deployment succeeded!'
        }
        failure {
            echo 'Build or Deployment failed!'
        }
    }
}