#!groovy

stage 'build base'
node{
    git url: 'git@github.com:springernature/sprcom-hybris.git'
    sh 'cd docker/Images/01_base && ./build'
}
stage 'build tomcat'
// test
stage 'build server'
// test

