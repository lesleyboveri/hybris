#!groovy

pipeline {
    agent { node { label 'hybris' } }
    tools { jdk 'jdk8' }
    stages {
        stage('build base docker image') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                checkout scm
                sh 'cd $WORKSPACE/docker/Images/01_base && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('build tomcat docker image') {
            steps {
                echo "Building Tomcat"
                sh 'cd $WORKSPACE/docker/Images/02_tomcat && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('build server docker image') {
            steps {
                echo "Building Server"
                sh 'cd $WORKSPACE/docker/Images/03_server && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('download hybris platform') {
            steps {
                echo "Downloading hybris.zip"
                sh './download.sh'
                echo "Extracting hybris.zip"
                sh './extract.sh'
            }
        }
        stage('build and test platform') {
            steps {
                sh '''
              echo "PATH = ${PATH}"
              echo "JAVA_HOME = ${JAVA_HOME}"
           '''
                sh './build_test.sh'
                junit '**/junit/*.xml'
            }
        }
        stage('create production artifacts') {
            steps {
                sh './production.sh'
            }
        }
        stage('Deploy to QA') {
            when {
                branch 'master'
            }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('Load tests') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('UI tests') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
    }
}
