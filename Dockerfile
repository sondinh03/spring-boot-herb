# Giai đoạn 1: Build ứng dụng bằng Maven
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Cấp quyền thực thi cho mvnw
RUN chmod +x mvnw

# Build project, tạo file JAR
RUN ./mvnw clean package -DskipTests

# Giai đoạn 2: Run ứng dụng với JDK
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file jar từ giai đoạn build
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render sẽ override bằng biến $PORT)
EXPOSE 8080

# Lệnh khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]