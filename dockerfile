# Use the official OpenJDK 11 image as the base image
FROM openjdk:11-jre-slim AS builder

# Set the working directory to /app
WORKDIR /app

# Copy the gradle files and build the project
#COPY gradlew gradlew.bat settings.gradle ./
#COPY gradle/ ./gradle/
#RUN chmod +x ./gradlew
#RUN ./gradlew build --no-daemon

# Copy the application JAR file to the runtime image
#FROM openjdk:11-jre-slim
#WORKDIR /app
RUN ls -al
COPY build/libs/bonheur-0.0.1-SNAPSHOT.jar app.jar

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]