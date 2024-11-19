# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/order-orchestrator-0.0.1-SNAPSHOT.jar order-orchestrator-service.jar

EXPOSE 9008

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/order-orchestrator-service.jar"]
