# Google Cloud Platform University

Carpet Showdown is a very simple project that demonstrates the usage of Docker
to deploy a web-app on the Google Cloud Platform.

Thanks to Docker layer of abstraction, it's easy to deploy the same application
on App Engine, Compute Engine or Container Engine.

Each solution has its advantages. Most of the time they can even be combined.
For instance, we'll use the App Engine's Cloud DataStore from all three.

## Run the web-app locally:

We are going to work on a simple web-app. Because we'd like to write code and test our application faster, we're going
to make sure it can be started out of the box like a standard java application.

```bash
mvn clean install
java -jar target/carpet.jar
```

## Run locally with Docker:

Packaging our application with Docker will make it easier to deploy anywhere.

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -p 8080:8080 dgageot/devoxxcarpet
```

## Run on GCE (Google Compute Engine) with docker-machine:

Docker machine makes it easy to create a remote server with Docker pre-installed.

```bash
docker-machine create --driver google --google-project carpet-devoxx-uk --google-zone europe-west1-d --google-machine-type n1-standard-1 carpet02

docker-machine config carpet02
docker $(docker-machine config carpet02) ps
docker $(docker-machine config carpet02) build -t dgageot/devoxxcarpet .
docker $(docker-machine config carpet02) run --rm -ti -p 80:8080 dgageot/devoxxcarpet
```

## Push docker image into Google Container registry

Instead of relying on a public Docker registry, one can have it's private
registry hosted on Google Cloud Platform without any configuration.

```bash
docker build -t dgageot/devoxxcarpet .
docker tag -f dgageot/devoxxcarpet gcr.io/carpet-devoxx-uk/devoxxcarpet
gcloud preview docker push gcr.io/carpet-devoxx-uk/devoxxcarpet
```

## Deploy on Kubernetes

```bash
gcloud alpha container get-credentials

kubectl create -f cluster/service.yaml
kubectl create -f cluster/pod.yaml

kubectl get services,pods

gcloud compute forwarding-rules list
gcloud compute firewall-rules create --allow=tcp:80 --target-tags=k8s-carpet-node k8s-carpet-node-80
```

Take a look at the logs.

### Start more nodes

```bash
kubectl proxy --www=cluster/static
open http:://localhost:8001/static/

kubectl scale --replicas=4 rc pod-carpet
```

### Cleanup Kubernetes Cluster

```bash
kubectl delete -f cluster/pod.yaml
kubectl delete -f cluster/service.yaml
```

## Deploy on App Engine Managed Vms:

First we might want to run locally with AppEngine's dev server.

```bash
gcloud preview app run app.yaml
```

Then really deploy the application.

```bash
gcloud --verbosity debug preview app deploy app.yaml
```

## Live debug

```bash
gcloud preview app modules delete default --version java
gcloud --verbosity debug preview app deploy --version=java app.yaml
```
