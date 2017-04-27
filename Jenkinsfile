#!groovy

pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '1'))
        disableConcurrentBuilds()
    }
    agent { node { label 'hybris' } }
    tools {
        jdk 'jdk8'
        ant 'apache-ant-1.9.1'
    }
    environment {
        PLATFORM_HOME = "$WORKSPACE/hybris/bin/platform"
        ANT_OPTS = "-Xmx512m -Dfile.encoding=UTF-8"
    }
    stages {
        stage('1 Build base Image') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo "echo '$PLATFORM_HOME'"
                checkout scm
                sh 'git clean -dfx'
                dir("$WORKSPACE/docker/Images/01_base") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('2 Build Tomcat Image') {
            steps {
                echo "Building Tomcat"
                dir("$WORKSPACE/docker/Images/02_tomcat") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('3 Build Server Image') {
            steps {
                echo "Building Server"
                dir("$WORKSPACE/docker/Images/03_server") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('4 Download Hybris Archive') {
            steps {
                timeout(900) {
                    dir("$WORKSPACE") {
                        echo "Downloading hybris.zip"
                        sh './download.sh'
                        echo "Extracting hybris.zip"
                        sh './extract.sh'
                    }
                }
            }
        }
        stage('5 Install Hybris Addons') {
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/install_addons.sh'
                }
            }
        }
        stage('6 Build and Test') {
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/build_test.sh'
                }
                dir("$WORKSPACE") {
                    junit '**/junit/*.xml'
                }
            }
        }
        stage('7 Create Production Artifacts') {
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/production.sh'
                }
            }
        }
        stage('8 Create final Image') {
            steps {
                dir("$WORKSPACE") {
                    sh './docker_production.sh -w $WORKSPACE -b $BUILD_ID'
                }
            }
        }
        stage('9 Deploy to QA') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('10 Load Testing') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('11 UI Testing') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('12 Tag branch') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('13 Deploy to Prod') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
    }
}
