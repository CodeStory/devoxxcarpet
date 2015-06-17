FROM maven:3.3.3-jdk-8
MAINTAINER David Gageot <david@gageot.net>

# Metadata
WORKDIR /home/devoxx
ENTRYPOINT ["./start.sh"]
EXPOSE 8080

# Warmup maven cache with an (not too) old version
RUN mkdir /tmp/carpet \
    && curl -SSL https://github.com/CodeStory/devoxxcarpet/archive/v3.tar.gz | tar xz --strip-components 1 -C /tmp/carpet \
    && cd /tmp/carpet \
    && mvn verify -DskipTests -Pprecompile \
    && rm -Rf /tmp/carpet

# Add all the sources and build
ADD . ./
RUN mvn verify -DskipTests -Pprecompile
