# Build:
#   docker build -t dgageot/devoxxcarpet .
#
# Run:
#   docker run --rm -ti dgageot/devoxxcarpet

FROM dgageot/maven

# Set working directory first
#
WORKDIR /home/jug

# Set run environment
#
ENV PROD_MODE true
ENV MEMORY 4
EXPOSE 8080
CMD java -DPROD_MODE=${PROD_MODE} -Xmx${MEMORY}G -jar target/jug.jar

# Add all sources from docker context
#
ADD . /home/jug

# Build the app
#
RUN mvn verify dependency:copy-dependencies -DskipTests
