node {
	try {

		stage('checkout project') {
		    checkout scm
        }

		stage('clean') {
		    sh "./gradlew clean"
        }
		stage('build') {
		    sh "./gradlew build"
        }
		stage('code analysis') {
			withSonarQubeEnv {
				sh './gradlew sonarqube'
			}
		}

		stage('mutation tests') {
		    sh './gradlew pitest'
        }
		stage('acceptance tests') {
		    sh './gradlew cucumber'
        }

		stage('publishing jars') {
			def server = Artifactory.server "artifactory_docker"

			def jars = """{
                "files": [
                {
                    "pattern": "build/libs/*.jar",
                    "target": "libs-snapshot-local/ovh/classregister/users/"
                }
                ]
            }"""

			def buildInfo = server.upload spec: jars

			server.publishBuildInfo buildInfo
		}

		stage('performance tests') {
	        sh './gradlew loadTest'
	        gatlingArchive()
	    }

	} catch (e) {
		currentBuild.result = "FAILURE"
		throw e;
	}
}
