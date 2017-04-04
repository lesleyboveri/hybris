#!groovy

pipeline {
  agent { node { label 'hybris' } }
  stages {
    stage('build base') {
      steps {
          echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
          checkout scm
          sh 'cd docker/Images/01_base && ./build'
      }
    }
    stage('build tomcat'){
      steps {
        echo "Building Tomcat"
      }
    }
    stage('build server'){
      steps{
        echo "Building Server"        
      }
    }
  }
}
