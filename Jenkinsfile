pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/DavidChan015/pruebas.git'
        EC2_HOST = '172.31.13.68'
        EC2_USER = 'ec2-user'
        IMAGE_NAME = 'demo-app:latest'
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Tests') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean test --no-daemon'
            }
        }

        stage('Build Jar') {
            steps {
                sh './gradlew bootJar --no-daemon'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(credentials: ['ec2-ssh-key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} "mkdir -p /home/${EC2_USER}/demo"
                        docker save ${IMAGE_NAME} | gzip > demo-app.tar.gz
                        scp -o StrictHostKeyChecking=no demo-app.tar.gz docker-compose.yml ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/demo/
                        ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} '
                            cd /home/${EC2_USER}/demo &&
                            gunzip -c demo-app.tar.gz | docker load &&
                            docker compose up -d --remove-orphans
                        '
                    '''
                }
            }
        }
    }

    post {
        always {
            junit testResults: '**/build/test-results/test/*.xml', allowEmptyResults: false
            sh 'rm -f demo-app.tar.gz || true'
        }
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
    }
}
