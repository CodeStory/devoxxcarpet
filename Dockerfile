FROM dgageot/maven

WORKDIR /home/devoxx

# warmup
ADD docker/old_version.tgz /home/devoxx
RUN mvn verify -DskipTests -Pprecompile && rm -Rf /home/devoxx

ENV PROD_MODE true
ENV MEMORY 2
ENV DATASTORE true
EXPOSE 8080
CMD java -DPROD_MODE=${PROD_MODE} -Xmx${MEMORY}G -jar target/carpet.jar

ADD . /home/devoxx

RUN mvn verify -DskipTests -Pprecompile

ENV DATASTORE false