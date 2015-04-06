FROM debian:wheezy
MAINTAINER David Gageot <david@gageot.net>

RUN apt-get update -qq && apt-get install -yqq \
  wget \
  curl

# Install java
RUN curl -s -k -L -C - -b "oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u40-b26/jdk-8u40-linux-x64.tar.gz | tar xfz - -C /

ENV JAVA_HOME /jdk1.8.0_40
ENV PATH $PATH:$JAVA_HOME/bin

# Install maven
RUN curl -s http://apache.crihan.fr/dist/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz | tar xzf - -C /

ENV MAVEN_HOME /apache-maven-3.1.1
ENV PATH $PATH:$MAVEN_HOME/bin

WORKDIR /home/devoxx

# warmup
ADD docker/old_version.tgz /home/devoxx
RUN mvn verify -DskipTests -Pprecompile && rm -Rf /home/devoxx

EXPOSE 8080
ENTRYPOINT ["./start.sh"]
ENV DATASTORE false

# build the app
ADD . /home/devoxx
RUN mvn verify -DskipTests -Pprecompile
