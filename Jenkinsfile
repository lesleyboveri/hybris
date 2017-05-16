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
                timeout(10) {
                    dir("$WORKSPACE") {
                        echo "Downloading hybris.zip"
                        sh './download.sh'
                        echo "Extracting hybris.zip"
                        sh './extract.sh'
                        echo 'Now we remove all zips'
                        sh 'rm -f *.zip'
                    }
                }
            }
        }
        stage('2 Install Hybris Addons & License') {
            steps {
                dir("$WORKSPACE") {
                    sh './install_addons.sh'
                    sh './install_license.sh'
                }
            }
        }
        stage('3 Build') {
            steps {
                dir("$WORKSPACE") {
                    sh './build.sh'
                }
            }
        }
        stage('4 Test') {
            steps {
                echo 'skipping test'
            }
        }
        stage('5 Create Production Artifacts') {
            when { branch 'master' }
            steps {
                dir("$WORKSPACE") {
                    sh './production.sh'
                }
            }
        }
        stage('6 Build base Image') {
            when { branch 'master' }
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo "echo '$PLATFORM_HOME'"
                
                dir("$WORKSPACE/docker/Images/01_base") {
                    sh 'docker build -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.base:latest -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.base:$BUILD_ID .'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.base:latest'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.base:$BUILD_ID'
                }
            }
        }
        stage('7 Build Tomcat Image') {
            when { branch 'master' }
            steps {
                echo "Building Tomcat"
                dir("$WORKSPACE/docker/Images/02_tomcat") {
                    sh 'docker build -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.tomcat:latest -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.tomcat:$BUILD_ID .'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.tomcat:latest'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.tomcat:$BUILD_ID'
                }
            }
        }
        stage('8 Build Server Image') {
            when { branch 'master' }
            steps {
                echo "Building Server"
                dir("$WORKSPACE/docker/Images/03_server") {
                    sh 'docker build -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.server -t docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.server:$BUILD_ID .'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.server:latest'
                    sh 'docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.server:$BUILD_ID'
                }
            }
        }
        stage('9 Create Platform Image') {
            when { branch 'master' }
            steps {
                dir("$WORKSPACE") {
                    sh './docker_production.sh $BUILD_ID'
                }
            }
        }
        stage('10 Deploy to QA') {
            when { branch 'master' }
            steps {
                sh "knife ssh 'name:sprcom-dev-hybris-app-01' 'sudo docker rm -f app-01 || true' -x sprcom-jenkins -C 1"
                sh "knife ssh 'name:sprcom-dev-hybris-app-01' 'sudo ./start.sh $BUILD_ID' -x sprcom-jenkins -C 1"
            }
        }
        stage('11 Load Testing') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('12 UI Testing') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('13 Tag branch') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
        stage('14 Deploy to Prod') {
            when { branch 'master' }
            steps { echo 'not yet implemented' }
        }
    }
    post {
        success {
            cleanWs()
        }
    }
}
