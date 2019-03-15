pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile.jenkins.maven'
            dir './jenkins/'
        }
    }
    environment {
        MAVEN_OPTS = '-Xmx1024m'
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
        stage('deploy') {
            when {
                buildingTag()
            }
            steps {
                sh 'mvn deploy'
            }
        }
    }
}