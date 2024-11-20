FROM adrianinetum2/smarttestengine:v1

# Set work directory
WORKDIR /app

# Copy tests and reports from the build context
COPY Tests /app/src/test/java/web/TestSuites
COPY Reports /app/resume-report

# Install dependencies (if needed)
RUN mvn clean install
