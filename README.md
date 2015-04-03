Run locally:

```bash
mvn verify dependency:copy-dependencies -DskipTests
java -DPROD_MODE=true -jar target/carpet.jar
```

Run locally with Docker:

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -p 8080:8080 dgageot/devoxxcarpet
```

Run locally with Docker using the CloudDataStore:

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -e DATASTORE=true dgageot/devoxxcarpet
```

Run on GCE with docker machine:

```bash
docker-machine create --driver google --google-project devoxxcarpet --google-zone europe-west1-d --google-machine-type n1-standard-1 carpet01
docker $(docker-machine config carpet01) ps
docker $(docker-machine config carpet01) build -t dgageot/devoxxcarpet .
docker $(docker-machine config carpet01) run --rm -ti -e DATASTORE=false -p 80:8080 dgageot/devoxxcarpet
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

 + Dockerfile switching
