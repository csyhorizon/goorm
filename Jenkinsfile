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

        // AWS S3 [아직 없음]
        AWS_ACCESS_KEY_ID = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
        S3_BUCKET_NAME = 'your-bucket-name'
        AWS_REGION = 'ap-northeast-2'

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
                        string(credentialsId: '', variable: ''),
                    ]) {
                        // sh 테스트 명령
                    }
                }
            }
        }
    }
}