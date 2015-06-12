FROM maven:3.3.3-jdk-8
MAINTAINER David Gageot <david@gageot.net>

WORKDIR /home/devoxx
ENTRYPOINT ["./start.sh"]
EXPOSE 8080

ADD . ./
RUN mvn verify -DskipTests -Pprecompile
