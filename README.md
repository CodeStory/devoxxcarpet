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

Run node version:

```bash
npm install
node server.js
```

Run node version with appengine dev mode:

```bash
npm install
gcloud preview app run .
```

Deploy node version on App Engine Managed Vms:

```bash
npm install
gcloud preview app deploy .
```

TODO:

 + Make docker-machine work
 + Dockerfile switching
 + gitignore
 + appengine adapter