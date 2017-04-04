#!groovy

pipeline {
  agent { node { label 'hybris' } }
  stages {
    stage('build base') {
      steps {
          echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
          checkout scm
          sh 'cd $WORKSPACE/docker/Images/01_base && ./build.sh'
      }
    }
    stage('build tomcat'){
      steps {
        echo "Building Tomcat"
          sh 'cd $WORKSPACE/docker/Images/02_tomcat && ./build.sh'
      }
    }
    stage('build server'){
      steps{
        echo "Building Server"        
          sh 'cd $WORKSPACE/docker/Images/03_server && ./build.sh'
      }
    }
  }
}
