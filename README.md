# Google Cloud Platform University

Carpet Showdown is a very simple project that demonstrates the usage of Docker
to deploy a web-app on the Google Cloud Platform.

Thanks to Docker layer of abstraction, it's easy to deploy the same application
on App Engine, Compute Engine or Container Engine.

Each solution has its advantages. Most of the time they can even be combined.
For instance, we'll use the App Engine's Cloud DataStore from all three.

## Run the web-app locally:

We are going to work on a simple web-app. Because we'd like to write code and test our application faster, we're going to make sure it can be started out of the box.

```bash
mvn clean verify -DskipTests
java -DPROD_MODE=true -jar target/carpet.jar
```




























Run locally with Docker:

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -p 8080:8080 dgageot/devoxxcarpet
```

Run locally with Docker using the CloudDataStore:

```bash
docker run --rm -ti -e DATASTORE=true -p 8080:8080 dgageot/devoxxcarpet
```

Run on GCE with docker machine:

```bash
docker-machine create --driver google --google-project devoxxcarpet --google-zone europe-west1-d --google-machine-type n1-standard-1 carpet02
docker $(docker-machine config carpet02) ps
docker $(docker-machine config carpet02) build -t dgageot/devoxxcarpet .
docker $(docker-machine config carpet02) run --rm -ti -e DATASTORE=false -p 80:8080 dgageot/devoxxcarpet
```

Create a docker machine with more scope:
```bash
docker-machine create --driver google --google-project devoxxcarpet --google-zone europe-west1-d --google-machine-type n1-standard-1 --google-scopes "https://www.googleapis.com/auth/compute,https://www.googleapis.com/auth/devstorage.read_write,https://www.googleapis.com/auth/datastore,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/cloud-platform" carpet03
```

Deploy on App Engine Managed Vms:

```bash
gcloud preview app modules delete default --version java
gcloud preview app deploy --version=java .
```

Run node version:

```bash
npm install
npm start
```

Run node version with appengine dev mode:

```bash
npm install
gcloud preview app run .
```

Deploy node version on App Engine Managed Vms:

```bash
mv Dockerfile Dockerfile.java
mv Dockerfile.node Dockerfile

npm install
gcloud preview app modules delete default --version node
gcloud preview app deploy --version=node .

mv Dockerfile Dockerfile.node
mv Dockerfile.java Dockerfile
```

Push docker image in Google Container registry

```bash
docker build -t dgageot/devoxxcarpet .
docker tag -f dgageot/devoxxcarpet gcr.io/devoxxcarpet/java
gcloud preview docker push gcr.io/devoxxcarpet/java
```

Deploy on Kubernetes

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

(Don't forget to commit the private key before pushing to Heroku.

```bash
heroku create
heroku buildpack:set https://github.com/heroku/heroku-buildpack-java
git push heroku master
heroku ps:scale web=1
heroku open

heroku config:set DATASTORE=true
```
