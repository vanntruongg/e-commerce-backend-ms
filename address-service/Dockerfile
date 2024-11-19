# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/address-service-0.0.1-SNAPSHOT.jar address-service.jar

EXPOSE 9006

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/address-service.jar"]
