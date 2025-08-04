pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'Java 17'
    }

    environment {
        DISPLAY = ':99'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/naveen23042000/TringAi-Bot-Test.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Automation') {
            steps {
                sh 'mvn exec:java -Dexec.mainClass="tringv4.leadSubmission"'
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}
