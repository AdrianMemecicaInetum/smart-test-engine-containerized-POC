FROM adrianinetum2/smart-test-engine-base:v1

WORKDIR /app

# Create necessary directories
RUN mkdir -p src/test/java/web/TestSuites
RUN mkdir -p resume-report

# Run Maven installation
RUN mvn clean install

# Set entry point
ENTRYPOINT ["mvn"]
