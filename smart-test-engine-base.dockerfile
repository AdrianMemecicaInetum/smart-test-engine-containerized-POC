FROM maven:3.9.6-eclipse-temurin-22-jammy
WORKDIR /app

# Install necessary packages and Chrome dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    git \
    unzip \
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
    && rm -rf /var/lib/apt/lists/*

COPY smart-test-engine-container /app

# Set up Chrome and ChromeDriver versions as build arguments
ARG CHROME_VERSION="130.0.6723.69"
ARG CHROMEDRIVER_VERSION="130.0.6723.69"

# Install Chrome
RUN mkdir -p src/test/resources/webFiles/ \
    && wget https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chrome-linux64.zip \
    && unzip chrome-linux64.zip -d /opt/ \
    && mv /opt/chrome-linux64 /opt/chrome \
    && rm chrome-linux64.zip \
    && chmod +x /opt/chrome/chrome

ENV CHROME_BIN="/opt/chrome/chrome"

# Install ChromeDriver
RUN wget https://storage.googleapis.com/chrome-for-testing-public/${CHROMEDRIVER_VERSION}/linux64/chromedriver-linux64.zip \
    && unzip chromedriver-linux64.zip -d /tmp/ \
    && mv /tmp/chromedriver-linux64/chromedriver src/test/resources/webFiles/chromedriver \
    && chmod +x src/test/resources/webFiles/chromedriver \
    && rm -rf /tmp/chromedriver-linux64 \
    && rm chromedriver-linux64.zip
