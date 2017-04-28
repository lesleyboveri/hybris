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
        stage('0 Setup workspace') {
                steps {
                  checkout scm
                  sh 'git clean -dfx'
                }
        }
        stage('1 Download Hybris Archive') {
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
        stage('2 Install Hybris Addons') {
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/install_addons.sh'
                }
            }
        }
        stage('3 Build and Test') {
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/build_test.sh'
                }
                dir("$WORKSPACE") {
                    junit '**/junit/*.xml'
                }
            }
        }
        stage('4 Create Production Artifacts') {
            when { branch 'master' }
            steps {
                dir("$PLATFORM_HOME") {
                    sh '$WORKSPACE/production.sh'
                }
            }
        }
        stage('5 Build base Image') {
            when { branch 'master' }
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo "echo '$PLATFORM_HOME'"
                
                dir("$WORKSPACE/docker/Images/01_base") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('6 Build Tomcat Image') {
            when { branch 'master' }
            steps {
                echo "Building Tomcat"
                dir("$WORKSPACE/docker/Images/02_tomcat") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('7 Build Server Image') {
            when { branch 'master' }
            steps {
                echo "Building Server"
                dir("$WORKSPACE/docker/Images/03_server") {
                    sh './build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
                }
            }
        }
        stage('8 Create Platform Image') {
            when { branch 'master' }
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
