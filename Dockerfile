# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the container
# Replace 'your-application.jar' with the actual name of your JAR file
# It's often found in the 'target' directory after building (e.g., target/my-app-0.0.1-SNAPSHOT.jar)
COPY target/your-application.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
