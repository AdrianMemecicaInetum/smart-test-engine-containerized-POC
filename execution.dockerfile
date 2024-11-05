FROM adrianinetum2/smarttestengine:v1
WORKDIR /app

ARG GIT_PAT
ARG GIT_REPO_URL

RUN mvn clean install

RUN git clone https://${GIT_PAT}@${GIT_REPO_URL} /home

RUN mkdir -p /app/src/test/java/web/TestSuites

WORKDIR /home
RUN mv Tests/* /app/src/test/java/web/TestSuites
RUN cp -r Reports/* /app/resume-report