FROM maven:3.9.6-eclipse-temurin-22-jammy
WORKDIR /app

# Install necessary packages
RUN apt-get update && apt-get install -y \
    wget \
    git \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Arguments for Git personal access token and repository URL
ARG GIT_PAT
ARG GIT_REPO_URL

RUN git clone https://${GIT_PAT}@${GIT_REPO_URL} /app

# Install required libraries for Chrome and ChromeDriver
RUN apt-get update && apt-get install -y \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libx11-6 \
    libxcomposite1 \
    libxrandr2 \
    libxdamage1 \
    libxi6 \
    libxtst6 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    libasound2 \
    libpangocairo-1.0-0 \
    libatk1.0-0 \
    libcups2 \
    libxss1 \
    libxshmfence1 \
    libdrm2 \
    libgbm1 \
    libpango1.0-0 \
    --no-install-recommends && rm -rf /var/lib/apt/lists/*

# Set up Chrome
RUN mkdir -p src/test/resources/webFiles/ \
    && wget https://storage.googleapis.com/chrome-for-testing-public/130.0.6723.69/linux64/chrome-linux64.zip \
    && unzip chrome-linux64.zip -d /opt/ \
    && mv /opt/chrome-linux64 /opt/chrome \
    && rm chrome-linux64.zip

ENV CHROME_BIN="/opt/chrome/chrome"
RUN chmod +x /opt/chrome/chrome

# Set up ChromeDriver
RUN wget https://storage.googleapis.com/chrome-for-testing-public/130.0.6723.69/linux64/chromedriver-linux64.zip \
    && unzip chromedriver-linux64.zip -d /tmp/ \
    && mv /tmp/chromedriver-linux64/chromedriver src/test/resources/webFiles/chromedriver \
    && chmod +x src/test/resources/webFiles/chromedriver \
    && rm -rf /tmp/chromedriver-linux64 \
    && rm chromedriver-linux64.zip

# Verify that chromedriver exists at the expected path
RUN ls -l /app/src/test/resources/webFiles/chromedriver

# Build and run tests
RUN mvn install
RUN mvn clean test

# Verify installations
RUN ls -l /opt/chrome/chrome
RUN /opt/chrome/chrome --version
RUN src/test/resources/webFiles/chromedriver --version
