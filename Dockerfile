FROM openjdk:17-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENV PORT=8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]


