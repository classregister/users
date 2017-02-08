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

    stage ('mutation tests')
    sh './gradlew pitest'

    stage ('acceptance tests')
    sh './gradlew cucumber'

    stage ('contract tests')
    sh './gradlew cdcTest'

  } catch(e){
    currentBuild.result = "FAILURE"
    throw e;
  }
}
