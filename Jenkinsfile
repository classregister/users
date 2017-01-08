node {
  try{
    stage 'checkout project'
    git credentialsId: 'd1a76f0b-4a13-4a78-a429-d0b3f43da1a8', url: 'git@bitbucket.org:KrzysztofJaniec/users.git'

    stage 'clean'
    sh "./gradlew clean"

    stage 'build'
    sh "./gradlew build"
  }catch(e){
    throw e;
  }
}