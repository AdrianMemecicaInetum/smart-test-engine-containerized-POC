FROM adrianinetum2/smart-test-engine-base:v1

ARG GIT_PAT
ARG GIT_REPO_URL

RUN mkdir -p /app/src/test/java/web/TestSuites
RUN git clone https://${GIT_PAT}@${GIT_REPO_URL} /home

WORKDIR /home
RUN cp -r Tests/* /app/src/test/java/web/TestSuites
RUN cp -r Reports/* /app/resume-report

WORKDIR /app

RUN mvn clean install