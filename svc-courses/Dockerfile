# Use a specific version of openjdk:17-jdk-alpine for better reproducibility
FROM openjdk:17-jdk-alpine3.14 as builder

# Set the working directory for the build stage
WORKDIR /app/svc-courses

# Copy the Maven configuration files. This is done separately to leverage Docker cache
COPY ./pom.xml /app
COPY ./svc-courses/.mvn ./.mvn
COPY ./svc-courses/mvnw .
COPY ./svc-courses/pom.xml .

# Install dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY ./svc-courses/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use the same specific version of openjdk:17-jdk-alpine for the final image
FROM openjdk:17-jdk-alpine3.14

# Set the working directory for the runtime stage
WORKDIR /app

# Create a directory for logs (if your application needs it)
RUN mkdir ./logs

# Copy the built jar from the builder stage
COPY --from=builder /app/svc-courses/target/svc-courses-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8002

# Run the application
CMD ["java", "-jar", "svc-courses-0.0.1-SNAPSHOT.jar"]