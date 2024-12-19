# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

EXPOSE 9003

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]
