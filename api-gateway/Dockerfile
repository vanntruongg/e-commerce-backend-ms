# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 9000

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]
