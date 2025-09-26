FROM openjdk:17-slim

# Install required packages
RUN apt-get update && \
    apt-get install -y curl unzip && \
    rm -rf /var/lib/apt/lists/*

# Set Maven version
ENV MAVEN_VERSION=3.9.6
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Download and install Maven
RUN curl -fsSL https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    | tar -xz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} $MAVEN_HOME

# Set working directory
WORKDIR /app

COPY . .

RUN mvn clean install
