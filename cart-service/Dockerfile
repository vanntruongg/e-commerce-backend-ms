# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk-alpine

# Copy file JAR vào container
COPY target/cart-service-0.0.1-SNAPSHOT.jar cart-service.jar

EXPOSE 9004

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/cart-service.jar"]
