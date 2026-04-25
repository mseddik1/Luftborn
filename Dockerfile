FROM maven:3.9.9-eclipse-temurin-21

RUN printf '%s\n' \
    'deb https://archive.ubuntu.com/ubuntu noble main restricted universe multiverse' \
    'deb https://archive.ubuntu.com/ubuntu noble-updates main restricted universe multiverse' \
    'deb https://archive.ubuntu.com/ubuntu noble-backports main restricted universe multiverse' \
    'deb https://security.ubuntu.com/ubuntu noble-security main restricted universe multiverse' \
    > /etc/apt/sources.list && \
    apt-get update && apt-get install -y --no-install-recommends \
    wget \
    curl \
    ca-certificates \
    gnupg \
    unzip \
    && mkdir -p /usr/share/keyrings \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub \
       | gpg --dearmor -o /usr/share/keyrings/google-linux.gpg \
    && printf '%s\n' \
       'deb [arch=amd64 signed-by=/usr/share/keyrings/google-linux.gpg] https://dl.google.com/linux/chrome/deb/ stable main' \
       > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

ENV CI=true

COPY pom.xml .
COPY src ./src


CMD ["mvn", "test", "-DsuiteXmlFile=src/test/suites/smoke/Master_Smoke.xml"]