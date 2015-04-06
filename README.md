Carpet Showdown is a very simple project that demonstrates the usage of Docker
to deploy a web-app on the Google Cloud Platform.

Thanks to Docker layer of abstraction, it's easy to deploy the same application
on App Engine, Compute Engine or Container Engine.

Each solution has its advantages. Most od the time they can even be combined.
For instance, we'll use the App Engine's Cloud DataStore from all three.
We will even demonstrate how to connect to the DataStore from another Cloud (heroku).


Run the web-app locally:

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
docker-machine create --driver google --google-project devoxxcarpet --google-zone europe-west1-d --google-machine-type n1-standard-1 carpet02
docker $(docker-machine config carpet02) ps
docker $(docker-machine config carpet02) build -t dgageot/devoxxcarpet .
docker $(docker-machine config carpet02) run --rm -ti -e DATASTORE=false -p 80:8080 dgageot/devoxxcarpet
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

Push docker image in Google Container registry

```bash
docker build -t dgageot/devoxxcarpet .
docker tag dgageot/devoxxcarpet gcr.io/devoxxcarpet/java
gcloud preview docker push gcr.io/devoxxcarpet/java
```

Deploy pod on Kubernetes

```bash
cd kube

gcloud alpha container kubectl create -f web-controller.json
gcloud alpha container kubectl get pods

gcloud alpha container kubectl create -f web-service.json
gcloud alpha container kubectl get services

gcloud compute firewall-rules create devoxxcarpet-80 --allow=tcp:80 --target-tags k8s-cluster-node

gcloud alpha container kubectl resize --replicas=2 rc web-controller
```

Cleanup Kubernetes Cluster

```bash
gcloud alpha container kubectl delete pod devoxxcarpet
gcloud alpha container kubectl get pods
```

Deploy on Heroku

```bash
heroku create
heroku buildpack:set https://github.com/heroku/heroku-buildpack-java
git push heroku master
heroku ps:scale web=1
heroku open
heroku config:set DATASTORE=true
```

TODO:

 + Dockerfile switching
