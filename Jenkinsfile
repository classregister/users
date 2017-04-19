pipeline {
	agent any

	stages {
		stage('build') {
			steps {
				checkout scm

				sh "./gradlew clean build"

				sh './gradlew cucumber'

				sh './gradlew pitest'

				sh './gradlew sonarqube'
			}
		}

		stage('performance tests') {
			steps {
				sh './gradlew loadTest'
			}

			post {
				success {
					gatlingArchive()
				}
			}
		}

		stage('publishing jars') {
			steps {
				script {
					def server = Artifactory.server "artifactory_docker"
					def branchName = env.BRANCH_NAME

					def jars = resolveArtifactoryUploadSpec(branchName)
					def buildInfo = server.upload spec: jars

					server.publishBuildInfo buildInfo
				}
			}
		}
	}


	post {
		always {
			deleteDir()
		}
	}
}


private static String resolveArtifactoryUploadSpec(String branchName) {
	def libsDirectory = "libs-snapshot-local"

	if (branchName.startsWith("release")) {
		libsDirectory = "libs-release-local"
	}

	def uploadSpec = """{
        "files": [
            {
                "pattern": "build/libs/*.jar",
                "target": "${libsDirectory}/ovh/classregister/users/"
            }
        ]
    }"""

	return uploadSpec;
}
