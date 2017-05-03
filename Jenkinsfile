pipeline {
	agent any

	parameters {
		booleanParam(defaultValue: false, description: 'Do you wanna release this version?', name: 'release')
		string(defaultValue: "", description: 'New release version', name: 'releaseVersion')
	}

	stages {
		stage('build') {
			steps {
				checkout scm

				sh "./gradlew clean build"

				sh './gradlew cucumber'

				sh './gradlew pitest'

				sh './gradlew sonarqube'

				sh './gradlew buildDocker -x check'
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

		stage('deploy') {
			when {
				expression {
					return env.RELEASE == "true"
				}
			}

			steps {
				script {
					withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: 'DOCKER_HUB',
					                  usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
						sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
						sh "docker tag users:${env.releaseVersion} classregister/users:${env.releaseVersion}"
						sh "docker tag users:${env.releaseVersion} classregister/users:latest"
						sh "docker push classregister/users:${env.releaseVersion}"
						sh 'docker push classregister/users:latest'
						sh 'docker logout'
					}
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
