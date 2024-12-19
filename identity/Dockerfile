# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/identity-service-0.0.1-SNAPSHOT.jar identity-service.jar

EXPOSE 9001

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/identity-service.jar"]
