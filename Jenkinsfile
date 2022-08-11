pipeline {
    agent any
    stages {
         stage('Initialize') {
            steps {
                script {
                    def dockerHome = tool 'my-docker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                }
            }
        }
        stage('docker build'){
            steps {

                dir('frontend/owo'){
                script{
                    docker.build('owo_front')
                 }
                }



                dir('backend'){
                script{
                    docker.build('owo_backend')
                    }
                }
            }
        }

        


        stage('docker run'){

            steps {

                    dir('frontend/owo'){
                sh 'docker ps -f name=owo_front -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -f name=owo_front -q | xargs -r docker container rm'
                sh 'docker images --no-trunc --all --quiet --filter="dangling=true" | xargs --no-run-if-empty docker rmi'
                sh 'docker run -d --name owo_front -p 8080:8080 owo_front:latest'
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