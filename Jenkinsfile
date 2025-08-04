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
                    echo "Using Java version:"
                    ${JAVA_HOME}/bin/java -version

                    echo "Setting JAVA_HOME explicitly"
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:$PATH

                    echo "Using Maven version:"
                    mvn -version

                    echo "Running Maven build"
                    mvn clean install
                '''
            }
        }

        stage('Run Automation Test') {
            steps {
                sh '''
                    echo "Running Test Class"
                    java -cp target/classes:target/dependency/* tringv4.leadSubmission
                '''
            }
        }
    }
}
