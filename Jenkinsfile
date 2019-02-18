pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile.jenkins.maven'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        MAVEN_OPTS = '-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn'
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn -X -B -DskipTests clean install'
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
}