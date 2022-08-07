FROM adoptopenjdk/openjdk11
CMD ["./mvnw", "clean", "package"]
VOLUME /tmp
EXPOSE 8282
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata