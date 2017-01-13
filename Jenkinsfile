node {
  try{

    stage 'checkout project'
    checkout scm

    stage 'clean'
    sh "./gradlew clean"

    stage 'build'
    sh "./gradlew build"

    stage ('code analysis') {
        withSonarQubeEnv {
          sh './gradlew sonarqube'
        }
    }

  }catch(e){
    currentBuild.result = "FAILURE"
    throw e;
  }
}