pipeline {
    agent any

    environment {
        // MySQL
        DB_PORT = '3306'
        DB_NAME = 'seot'

        MASTER_DB_HOST = 'mysql-master'
        SUB_DB_HOST = 'mysql-slave'

        // Redis
        REDIS_HOST = 'redis-cache'
        REDIS_PORT = '6379'

        // --- 수정 끝 ---
        MONGO_HOST = 'mongodb'
        MONGO_PORT = '27017'
        MONGO_DB = 'seot'

        // --- 수정 끝 ---
        S3_BUCKET_NAME = 'seotbucket'
        CLOUD_AWS_REGION_STATIC = 'ap-northeast-2'

        // JWT (ID만 환경변수로 설정)
        JWT_SECRET_CREDENTIAL_ID = 'jwt-secret-text'

        // Discord (ID만 환경변수로 설정)
        DISCORD_WEBHOOK_URL_CREDENTIAL_ID = 'DISCORD_WEBHOOK_URL'

        // Github (ID만 환경변수로 설정)
        GITHUB_REPO_URL_CREDENTIAL_ID = 'github-repo-url'
        GITHUB_BRANCH_CREDENTIAL_ID = 'github-branch'

        // GCP (ID만 환경변수로 설정)
        GCP_SSH_CREDENTIAL_ID = 'gcp-ssh-key-credential'

        // Docker (ID와 사용자 이름은 환경변수로 설정)
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

        stage('Build Only') {
            steps {
                script {
                    sh "./gradlew clean build -x test --no-daemon"
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
                        string(credentialsId: 'host-vm-user', variable: 'GCP_VM_USER'),
                        string(credentialsId: 'host-vm-ip-address-or-hostname', variable: 'GCP_VM_HOST'),

                        string(credentialsId: 'mysql-username', variable: 'MYSQL_MASTER_USERNAME'),
                        string(credentialsId: 'mysql-password', variable: 'MYSQL_MASTER_PASSWORD'),

                        string(credentialsId: 'mysql-username', variable: 'MYSQL_SLAVE_USERNAME'),
                        string(credentialsId: 'mysql-password', variable: 'MYSQL_SLAVE_PASSWORD'),

                        string(credentialsId: 'redis-password', variable: 'REDIS_PASSWORD'),

                        string(credentialsId: 'mongo-username', variable: 'MONGO_USER'),
                        string(credentialsId: 'mongo-password', variable: 'MONGO_PASSWORD'),

                        string(credentialsId: 'jwt-secret-text', variable: 'JWT_SECRET'),

                        string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY'),
                        string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_KEY')
                    ]) {
                        sh '''
                        ssh-keygen -R ${GCP_VM_HOST} || true # 호스트 키 제거 실패 시에도 파이프라인 중단 방지

ssh -o StrictHostKeyChecking=no ${GCP_VM_USER}@${GCP_VM_HOST} << EOF
docker pull ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
docker stop seot-backend || true
docker rm seot-backend || true
docker run -d \\
    -p 8080:8080 \\
    --name seot-backend \\
    --network seot \\
    -e MYSQL_DATABASE_MASTER_URL="jdbc:mysql://${MASTER_DB_HOST}:${DB_PORT}/${DB_NAME}" \\
    -e MYSQL_MASTER_USERNAME="${MYSQL_MASTER_USERNAME}" \\
    -e MYSQL_MASTER_PASSWORD="${MYSQL_MASTER_PASSWORD}" \\
    -e MYSQL_DATABASE_SLAVE_URL="jdbc:mysql://${SUB_DB_HOST}:${DB_PORT}/${DB_NAME}" \\
    -e MYSQL_SLAVE_USERNAME=${MYSQL_SLAVE_USERNAME} \\
    -e MYSQL_SLAVE_PASSWORD=${MYSQL_SLAVE_PASSWORD} \\
    -e SPRING_REDIS_HOST=${REDIS_HOST} \\
    -e SPRING_REDIS_PORT=${REDIS_PORT} \\
    -e SPRING_REDIS_PASSWORD=${REDIS_PASSWORD} \\
    -e SPRING_DATA_REDIS_HOST=${REDIS_HOST} \\
    -e SPRING_DATA_REDIS_PORT=${REDIS_PORT} \\
    -e SPRING_DATA_REDIS_PASSWORD=${REDIS_PASSWORD} \\
    -e SPRING_DATA_MONGODB_USERNAME=${MONGO_USER} \\
    -e SPRING_DATA_MONGODB_PASSWORD=${MONGO_PASSWORD} \\
    -e SPRING_DATA_MONGODB_HOST=${MONGO_HOST} \\
    -e SPRING_DATA_MONGODB_PORT=${MONGO_PORT} \\
    -e SPRING_DATA_MONGODB_DATABASE=${MONGO_DB} \\
    -e JWT_SECRET=${JWT_SECRET} \\
    -e CLOUD_AWS_CREDENTIALS_ACCESS_KEY=${AWS_ACCESS_KEY} \\
    -e CLOUD_AWS_CREDENTIALS_SECRET_KEY=${AWS_SECRET_KEY} \\
    -e CLOUD_AWS_REGION_STATIC=${CLOUD_AWS_REGION_STATIC} \\
    -e cloud.aws.credentials.access-key=${AWS_ACCESS_KEY} \\
    -e cloud.aws.credentials.secret-key=${AWS_SECRET_KEY} \\
    -e cloud.aws.region.static=${CLOUD_AWS_REGION_STATIC} \\
    -e cloud.aws.s3.bucketName=${S3_BUCKET_NAME} \\
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