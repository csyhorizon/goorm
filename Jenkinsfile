pipeline {
    agent any

    environment {
        // DockerHub 환경변수
        // DOCKER_REGISTRY = 'registry-url'
        // DOCKER_IMAGE = 'image-name'
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
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    sh 'docker build -t clone-app:latest .'

                    // sh "docker tag clone-app:latest ${DOCKER_REGISTRY}/clone-app:latest"
                    // sh "docker push ${DOCKER_REGISTRY}/clone-app:latest"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
