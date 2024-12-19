# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

EXPOSE 9002

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/product-service.jar"]
