pipeline {
    agent any

    tools {
        jdk 'Java 17'             // You must configure this in Jenkins Global Tool Configuration
        maven 'Maven-3.9.6'       // Also configure this in Jenkins if not done already
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/naveen23042000/TringAi-Bot-Test.git' // change this if your repo URL is different
            }
        }

        stage('Build Project') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Automation Test') {
            steps {
                // Replace tringv4.leadSubmission with your actual fully qualified class if different
                sh 'java -cp target/classes:target/dependency/* tringv4.leadSubmission'
            }
        }
    }
}
