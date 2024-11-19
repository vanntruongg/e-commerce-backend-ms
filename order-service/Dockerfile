# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

EXPOSE 9005

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/order-service.jar"]
