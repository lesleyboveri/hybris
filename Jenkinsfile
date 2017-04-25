#!groovy

pipeline {
    agent { node { label 'hybris' } }
    tools { jdk 'jdk8' }
    stages {
        stage('Build base Docker Image') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                checkout scm
                sh 'cd $WORKSPACE/docker/Images/01_base && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('Build Tomcat Docker Image') {
            steps {
                echo "Building Tomcat"
                sh 'cd $WORKSPACE/docker/Images/02_tomcat && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('Build Server Docker Image') {
            steps {
                echo "Building Server"
                sh 'cd $WORKSPACE/docker/Images/03_server && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
            }
        }
        stage('Download Hybris Platform') {
            steps {
                echo "Downloading hybris.zip"
                sh './download.sh'
                echo "Extracting hybris.zip"
                sh './extract.sh'
            }
        }
        stage('Install Addons') {
            steps {
                sh 'ant addoninstall -Daddonnames="acceleratorwebservicesaddon" -DaddonStorefront.ycommercewebservices="ycommercewebservices"'
                sh 'ant addoninstall -Daddonnames="commerceorgsamplesaddon,orderselfserviceaddon,assistedservicestorefront,customerticketingaddon,promotionenginesamplesaddon,textfieldconfiguratortemplateaddon,liveeditaddon,smarteditaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"'
                sh 'ant addoninstall -Daddonnames="sapcoreaddon" -DaddonStorefront.yacceleratorstorefront="springernaturestorefront"'
                sh 'ant addoninstall -Daddonnames="worldpayaddon,worldpaynotificationaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"'
            }
        }
        stage('Build and Test') {
            steps {
                sh '''
                  echo "PATH = ${PATH}"
                  echo "JAVA_HOME = ${JAVA_HOME}"
                '''
                sh './build_test.sh'
                junit '**/junit/*.xml'
            }
        }
        stage('Create Production Artifacts') {
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
        stage('Load Testing') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('UI Testing') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
        stage('Deploy to Prod') {
            when { branch 'master' }
            steps {
                echo 'not yet implemented'
            }
        }
    }
}
