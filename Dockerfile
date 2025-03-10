# Use Maven image to build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download the dependencies
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use OpenJDK to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file from the build image
COPY --from=build /app/target/binance-alerts-0.0.1.jar app.jar

# Expose the port
EXPOSE 8080

ENV SPRING_APPLICATION_JSON='{"server.address":"0.0.0.0"}'


# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
