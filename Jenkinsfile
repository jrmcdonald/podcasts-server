pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile.jenkins.maven'
            dir './jenkins/'
            args '-v $HOME/.m2:/root/.m2'
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