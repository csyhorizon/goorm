pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "c1one-app"
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
                    if (isUnix()) {
                        sh './gradlew clean build --no-daemon -x test'
                        sh './gradlew test --no-daemon'
                    } else {
                        bat 'gradlew.bat clean build -x test'
                        bat 'gradlew.bat test'
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
                    // DOCKER_REGISTRY 변수는 Jenkins Job 설정에서 환경 변수로 주입됩니다.
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
                    // DEPLOY_SERVER_USER, DEPLOY_SERVER_IP, APP_PORT, APP_CONTAINER_NAME 등은
                    // Jenkins Job 설정에서 환경 변수로 주입됩니다.
                    echo "Deploying Docker image ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} to VM"

                    withCredentials([
                        string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD_ENV'),
                        string(credentialsId: 'JWT_SECRET_KEY', variable: 'JWT_SECRET_ENV'),
                        sshUserPrivateKey(credentialsId: 'my-gcp-ssh-key', keyFileVariable: 'SSH_KEY_PATH')
                    ]) {
                        sh "ssh -i ${SSH_KEY_PATH} ${env.DEPLOY_SERVER_USER}@${env.DEPLOY_SERVER_IP} 'docker stop ${env.APP_CONTAINER_NAME} || true'"
                        sh "ssh -i ${SSH_KEY_PATH} ${env.DEPLOY_SERVER_USER}@${env.DEPLOY_SERVER_IP} 'docker rm ${env.APP_CONTAINER_NAME} || true'"

                        sh "ssh -i ${SSH_KEY_PATH} ${env.DEPLOY_SERVER_USER}@${env.DEPLOY_SERVER_IP} 'docker pull ${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}'"

                        // 새로운 Docker 컨테이너 실행 시 환경 변수 주입
                        sh "ssh -i ${SSH_KEY_PATH} ${env.DEPLOY_SERVER_USER}@${env.DEPLOY_SERVER_IP} 'docker run -d " +
                           "-p ${env.APP_PORT}:${env.APP_PORT} " +
                           "--name ${env.APP_CONTAINER_NAME} " +
                           "-e DB_URL=\"${env.DB_URL}\" " +
                           "-e DB_USERNAME=\"${env.DB_USERNAME}\" " +
                           "-e DB_PASSWORD=\"${DB_PASSWORD_ENV}\" " +
                           "-e JWT_SECRET=\"${JWT_SECRET_ENV}\" " +
                           "-e JWT_EXPIRATION_MS=\"${env.JWT_EXPIRATION_MS}\" " +
                           "${env.DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}'"
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