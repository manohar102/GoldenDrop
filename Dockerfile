# Use a base image with Java 17 and Gradle installed
FROM amazoncorretto:17-alpine-jdk AS builder

# Install wget
RUN apk update && apk add --no-cache wget

# Install Gradle
ENV GRADLE_VERSION=7.4
# Set the working directory in the container
WORKDIR /app

# Download Gradle distribution
RUN wget -q --show-progress --progress=bar:force:noscroll --https-only --timestamping \
    "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" && \
    unzip "gradle-${GRADLE_VERSION}-bin.zip" && \
    rm "gradle-${GRADLE_VERSION}-bin.zip"

ENV GRADLE_HOME=/app/gradle-$GRADLE_VERSION
ENV PATH=$PATH:$GRADLE_HOME/bin
# Set Gradle wrapper executable
#RUN ln -s "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle

RUN gradle --version

# Copy the Gradle files needed for dependency resolution
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

# Copy the source code of the project
COPY src ./src

RUN gradle spotlessApply

# Run the Gradle wrapper to resolve dependencies and build the project
RUN gradle build --no-daemon -x test

# Use a lightweight base image for the runtime
FROM amazoncorretto:17-alpine-jdk

# Set the working directory
WORKDIR /app

# Copy the built application JAR file from the builder stage
COPY --from=builder /app/build/libs/GoldenDrop-0.0.1-SNAPSHOT.jar /app/GoldenDrop-0.0.1-SNAPSHOT.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/GoldenDrop-0.0.1-SNAPSHOT.jar"]
