# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar

EXPOSE 9007

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/inventory-service.jar"]
