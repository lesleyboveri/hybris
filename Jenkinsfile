#!groovy

pipeline {
    agent { node { label 'hybris' } }
    tools { jdk 'jdk8', ant 'apache-ant-1.9.1' }
    environment {
        PLATFORM_HOME = "$WORKSPACE/hybris/bin/platform"
        ANT_OPTS="-Xmx512m -Dfile.encoding=UTF-8"
    }
    stages {
        stage('1) Build base Image') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                checkout scm
                sh 'git clean -dfx'
                sh 'cd $WORKSPACE/docker/Images/01_base && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('2) Build Tomcat Image') {
            steps {
                echo "Building Tomcat"
                sh 'cd $WORKSPACE/docker/Images/02_tomcat && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('3) Build Server Image') {
            steps {
                echo "Building Server"
                sh 'cd $WORKSPACE/docker/Images/03_server && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('4) Download Hybris Archive') {
            steps {
                echo "Downloading hybris.zip"
                sh './download.sh'
                echo "Extracting hybris.zip"
                sh './extract.sh'
            }
        }
        stage('5) Install Hybris Addons') {
            steps {
                dir '$PLATFORM_HOME'
                sh './install_addons.sh'
            }
        }
        stage('6) Build and Test') {
            steps {
                sh '''
                  echo "PATH = ${PATH}"
                  echo "JAVA_HOME = ${JAVA_HOME}"
                '''
                sh './build_test.sh'
                junit '**/junit/*.xml'
            }
        }
        stage('7) Create Production Artifacts') {
            steps {
                sh './production.sh'
            }
        }
        stage('8) Create final Image') {
          steps {
              sh './docker_production.sh -w $WORKSPACE -b $BUILD_ID'
          }
        }
        stage('9) Deploy to QA') {
            when {
                branch 'master'
            }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('10) Load Testing') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('11) UI Testing') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('12) Tag branch') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('13) Deploy to Prod') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
    }
}
