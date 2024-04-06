pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '10')) // Retain history on the last 10 builds
        timestamps() // Append timestamps to each line
        timeout(time: 20, unit: 'MINUTES') // Set a timeout on the total execution time of the job
    }
    agent any // Run on any available agent
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('build') {
            steps {
                script {
                    
                    sh 'pwd'
                    
                    sh './gradlew wrapper --gradle-version=8.7 --distribution-type=bin clean build'
                }
            }
        }

        stage('Unit Testing') {
            steps {
                script {
                    sh 'gradle wrapper --gradle-version=8.7 --distribution-type=bin test'
                }
                post {
                // Define actions based on the outcome of the Test stage
                always {
                    // Actions that should always happen regardless of the outcome.
                    // For example, you could archive test reports.
                    archiveArtifacts artifacts: 'build/reports/tests/test/', allowEmptyArchive: true
                }
                success {
                    // Actions to perform if the Test stage succeeds.
                    echo 'Tests passed successfully.'
                }
                failure {
                    // Actions to perform if the Test stage fails.
                    echo 'Tests failed.'
                }
            }

            }
        }
        stage('Docker Login') {
            steps {
                script {
                    

                    // Build the Docker image with the commit ID as a build argument
                    
                 
                    sh "echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin"
                   
                }
            }
        }

        stage('Deploy to Docker Registry') {
            steps {
                script {
                    def commitId = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

                    // Build the Docker image with the commit ID as a build argument
                    sh "docker buildx build --platform=linux/amd64 --push --tag  $DOCKER_USERNAME/math-add-subtract:${commitId} -f ./Dockerfile ."
                 
                  
                }
            }
        }

                stage('Deploy to K8S') {
            steps {
                script {
                    def commitId = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

                    // Build the Docker image with the commit ID as a build argument
                    sh "sed -i 's/TAG_HERE/"+commitId+"/g' deployment.yml"
                    sh "kubectl apply -f deployment.yml -n weclouddata"
                    
                    
                 
                  
                }
            }
        }
    }
    post {
        failure {
            script {
                def msg = "Build error for ${env.JOB_NAME} ${env.BUILD_NUMBER} (${env.BUILD_URL})"
               
            }
        }
    }
}
