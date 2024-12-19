# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/rating-service-0.0.1-SNAPSHOT.jar rating-service.jar

EXPOSE 9009

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/rating-service.jar"]
