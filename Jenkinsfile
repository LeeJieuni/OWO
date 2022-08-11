pipeline {
    agent any
    stages {
        stage('FrontEnd'){
            steps {

                dir('frontend/owo'){
                script{
                    docker.build('owo_front')
                 }
                }

                dir('frontend/owo'){
                                sh 'docker ps -f name=owo_front -q | xargs --no-run-if-empty docker container stop'
                                sh 'docker container ls -a -f name=owo_front -q | xargs -r docker container rm'
                                sh 'docker images --no-trunc --all --quiet --filter="dangling=true" | xargs --no-run-if-empty docker rmi'
                                sh 'docker run -d --name owo_front -p 8080:8080 owo_front:latest'
                 }

            }
        }

        


        stage('BackEnd'){

            steps {

             dir('backend'){
                      script{
                         docker.build('owo_backend')
                      }
             }


              dir('backend'){
              sh 'docker ps -f name=owo_backend -q | xargs --no-run-if-empty docker container stop'
              sh 'docker container ls -a -f name=owo_backend -q | xargs -r docker container rm'
              sh 'docker images --no-trunc --all --quiet --filter="dangling=true" | xargs --no-run-if-empty docker rmi'
              sh 'docker run -d --name owo_backend --net {네트워크명} -p 8282:8282 owo_backend:latest'
              }

             }

            }


        }

}