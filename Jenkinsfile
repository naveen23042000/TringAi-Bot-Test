pipeline {
    agent any

    environment {
        JAVA_HOME = "/opt/jdk-17"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/naveen23042000/TringAi-Bot-Test.git'
            }
        }

        stage('Build Project') {
            steps {
                sh '''
                    echo "Java version:"
                    java -version

                    echo "Maven version:"
                    mvn -version

                    echo "Running Maven build..."
                    mvn clean install
                '''
            }
        }

        stage('Run Automation Test') {
            steps {
                sh '''
                    echo "Executing leadSubmission class..."
                    java -cp target/classes:target/dependency/* tringv4.leadSubmission
                '''
            }
        }
    }
}
