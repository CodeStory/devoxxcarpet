# TODO

- Try to use latest datastore Api
- Restore warmup Docker build?
- ...

# Prepare Demos

- Local
- Docker
- Managed VMS
- Logs -> BigQuery
- Live debug
- Kubernetes, lots of nodes with load balancing
- Connect to a different backend
- Firebase backend deployed on CDN

# Possible

- Kubernetes: show what's going on with firebase bridge
- Auto scaling

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
mvn clean verify -DskipTests
java -DPROD_MODE=true -jar target/carpet.jar
```

## Run locally with Docker:

Packaging our application with Docker will make it easier to deploy anywhere.

```bash
docker build -t dgageot/devoxxcarpet .
docker run --rm -ti -p 8080:8080 dgageot/devoxxcarpet
```

## Run locally with Docker using the CloudDataStore:

```bash
docker run --rm -ti -e DATASTORE=true -p 8080:8080 dgageot/devoxxcarpet
```

## Run on GCE (Google Compute Engine) with docker-machine:

Docker machine makes it easy to create a remote server with Docker pre-installed.

```bash
docker-machine create --driver google --google-project code-story-blog --google-zone europe-west1-d --google-machine-type n1-standard-1 carpet02

docker-machine config carpet02
docker $(docker-machine config carpet02) ps
docker $(docker-machine config carpet02) build -t dgageot/devoxxcarpet .
docker $(docker-machine config carpet02) run --rm -ti -e DATASTORE=false -p 80:8080 dgageot/devoxxcarpet
```

One can create a docker machine with more scope:

```bash
docker-machine create --driver google --google-project code-story-blog --google-zone europe-west1-d --google-machine-type n1-standard-1 --google-scopes "https://www.googleapis.com/auth/compute,https://www.googleapis.com/auth/devstorage.read_write,https://www.googleapis.com/auth/datastore,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/cloud-platform" carpet03
```

## Push docker image into Google Container registry

Instead of relying on a public Docker registry, one can have it's private
registry hosted on Google Cloud Platform without any configuration.

```bash
docker build -t dgageot/devoxxcarpet .
docker tag -f dgageot/devoxxcarpet gcr.io/code-story-blog/devoxxcarpet
gcloud preview docker push gcr.io/code-story-blog/devoxxcarpet
```

## Deploy on Kubernetes

```bash
gcloud alpha container get-credentials

kubectl create -f cluster/service.yaml
kubectl create -f cluster/pod.yaml

kubectl get services,pods

gcloud compute forwarding-rules list

kubectl scale --replicas=4 rc pod-carpet
```

### Cleanup Kubernetes Cluster

```bash
kubectl delete -f cluster/pod.yaml
kubectl delete -f cluster/service.yaml
```









Deploy on App Engine Managed Vms:

```bash
gcloud preview app modules delete default --version java
gcloud preview app deploy --version=java .
```
