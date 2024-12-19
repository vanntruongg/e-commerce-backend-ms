# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

# Expose cổng 8761 của Eureka
EXPOSE 8761

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
