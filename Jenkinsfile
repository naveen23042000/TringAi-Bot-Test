pipeline {
    agent any

    tools {
        jdk 'Java 17'
        maven 'Maven-3.9.6'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/naveen23042000/TringAi-Bot-Test.git'
            }
        }

        stage('Build Project') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Automation Test') {
            steps {
                sh 'java -cp target/classes:target/dependency/* tringv4.leadSubmission'
            }
        }
    }
}
