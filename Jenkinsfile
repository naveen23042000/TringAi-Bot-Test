pipeline {
    agent any

    environment {
        JAVA_HOME = "/opt/jdk-17"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    tools {
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
                sh 'echo Using Java version: && java -version'
                sh 'echo Using Maven version: && mvn -version'
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
