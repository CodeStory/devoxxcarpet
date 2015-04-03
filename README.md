Run locally:

```bash
mvn verify dependency:copy-dependencies -DskipTests
java -DPROD_MODE=true -jar target/carpet.jar
```

Run on GCE with docker machine:

```bash
docker-machine create --driver google --google-project vmruntime-demo --google-zone us-central1-a carpet01
```