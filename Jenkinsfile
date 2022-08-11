pipeline {
    agent any
    stages {
        stage('FrontEnd'){
            steps {

                dir('frontend/owo'){
                script{
                    docker.build('front')
                 }
                }

                dir('frontend/owo'){
                                sh 'docker ps -f name=front -q | xargs --no-run-if-empty docker container stop'
                                sh 'docker container ls -a -f name=front -q | xargs -r docker container rm'
                                sh 'docker images --no-trunc --all --quiet --filter="dangling=true" | xargs --no-run-if-empty docker rmi'
                                sh 'docker run -d --name front -p 8080:8080 front:latest'
                 }

            }
        }

        


        stage('BackEnd'){


            steps {

             dir('backend'){
                      script{
                         docker.build('backend')
                      }
             }


              dir('backend'){
              sh 'docker ps -f name=backend -q | xargs --no-run-if-empty docker container stop'
              sh 'docker container ls -a -f name=backend -q | xargs -r docker container rm'
              sh 'docker images --no-trunc --all --quiet --filter="dangling=true" | xargs --no-run-if-empty docker rmi'
              sh 'docker run -d --name backend --net {네트워크명} -p 8282:8282 backend:latest'
              }

             }

            }


        }

}