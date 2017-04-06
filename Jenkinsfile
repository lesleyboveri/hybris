#!groovy

pipeline {
  agent { node { label 'hybris' } }
  stages {
    stage('build base') {
      steps {
          echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
          checkout scm
          sh 'cd $WORKSPACE/docker/Images/01_base && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID' 
      }
    }
    stage('build tomcat'){
      steps {
        echo "Building Tomcat"
          sh 'cd $WORKSPACE/docker/Images/02_tomcat && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
      }
    }
    stage('build server'){
      steps{
        echo "Building Server"        
          sh 'cd $WORKSPACE/docker/Images/03_server && ./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$BUILD_ID'
      }
    }
    stage('download hybris platform'){
      echo "Downloading hybris.zip"
      }
  }
}
