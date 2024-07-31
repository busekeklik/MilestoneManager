# Base image
FROM openjdk:17

# Copy the JAR file to the Docker image
COPY MilestoneManager-0.0.1-SNAPSHOT.jar app.jar

# Entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
