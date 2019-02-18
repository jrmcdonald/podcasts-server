pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        MAVEN_OPTS = '-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn'
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn -X -B -DskipTests clean installs '
            }
        }
        stage('test') { 
            steps {
                sh 'mvn test' 
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
            }
        }
    }
}m