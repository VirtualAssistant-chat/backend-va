FROM openjdk:11
COPY ./build/libs /app
WORKDIR /app
ENTRYPOINT ["java","-jar","virtualAssistant-0.0.1-SNAPSHOT.jar"]
