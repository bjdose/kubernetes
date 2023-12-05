# Use ARG for parameters that are used in FROM instructions
ARG SVC_NAME=svc-users

# Use a specific version of the alpine image for reproducibility
FROM openjdk:17-jdk-alpine3.14 as builder

# Set the working directory for the build stage
ARG SVC_NAME

WORKDIR /app/$SVC_NAME

# COPY with more specific paths can improve build cache utilization
COPY ./pom.xml /app
COPY ./$SVC_NAME/.mvn ./.mvn
COPY ./$SVC_NAME/mvnw .
COPY ./$SVC_NAME/pom.xml .

# Install dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY ./$SVC_NAME/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use the same specific version of the alpine image for consistency
FROM openjdk:17-jdk-alpine3.14

ARG SVC_NAME

WORKDIR /app

# COPY the jar file in one step
COPY --from=builder /app/svc-users/target/svc-users-0.0.1-SNAPSHOT.jar .

# Use ENV for environment variables
ENV PORT_APP=8001

# Expose the port using the ENV variable
EXPOSE $PORT_APP

# Use the ENV variable in the CMD instruction
CMD ["java", "-jar", "/app/svc-users-0.0.1-SNAPSHOT.jar"]