FROM openjdk:11-jre-slim
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew

FROM openjdk:11-jre-slim
COPY --from=builder build/libs/*.jar app.jar

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=local

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]