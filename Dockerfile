FROM openjdk:11-jre-slim
WORKDIR /app
COPY /target/advlib-react-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java","-jar","advlib-react-api-0.0.1-SNAPSHOT.jar"]