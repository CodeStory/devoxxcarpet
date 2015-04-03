# Build:
#   docker build -t dgageot/devoxxcarpet .
#
# Run:
#   docker run --rm -ti dgageot/devoxxcarpet

FROM dgageot/maven

# Set working directory first
#
WORKDIR /home/devoxx

# Warmup maven by building an old version that we don't change often
#
ADD docker/old_version.tgz /home/devoxx
RUN mvn verify -DskipTests && rm -Rf /home/devoxx

# Set run environment
#
ENV PROD_MODE true
ENV MEMORY 4
EXPOSE 8080
CMD java -DPROD_MODE=${PROD_MODE} -Xmx${MEMORY}G -jar target/carpet.jar

# Add all sources from docker context
#
ADD . /home/devoxx

# Build the app
#
RUN mvn verify dependency:copy-dependencies -DskipTests
