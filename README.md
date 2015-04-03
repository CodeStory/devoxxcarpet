Run locally:

```bash
mvn verify dependency:copy-dependencies -DskipTests
java -DPROD_MODE=true -jar target/carpet.jar
```

Run locally with Docker:

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti dgageot/devoxxcarpet
```

Run locally with Docker using the CloudDataStore:

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -e DATASTORE=true dgageot/devoxxcarpet
```

Run on GCE with docker machine:

```bash
docker-machine create --driver google --google-project vmruntime-demo --google-zone us-central1-a carpet01
```

TODO:

 + Make docker-machine work
