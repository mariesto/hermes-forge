## Background
This is a sample project to learn k8s which run locally with minikube. What to cover :
- Basic operation of k8s and kubectl
- Load testing using K6 to simulate HPA
- Application monitoring in Grafana Dashboard (ID: 19004)

## Pre-requisite 
- Docker installed
- minikube installed
- kubectl installed
- Node.JS installed

## How to Use
- Spin up the docker compose to spawn the PostgresDB
```shell
docker compose up -d
```

- Verify the metrics-server enabled 
```shell
minikube addons list | grep metrics-server

# | metrics-server | minikube | enabled ✅   | Kubernetes                     |
```

- Start the minikube 
```shell
minikube start
```

- Create the deployment
```shell
kubectl apply -f kube/ -R
```

<hr>

In order to test the API, you need to :
- Obtain the URL of the application and combine with the API path :
```shell
minikube service order-service --url
# http://127.0.0.1:57844
```
- Sample request :
```shell
curl -X POST http://127.0.0.1:57844/api/v1/orders \
     -H "Content-Type: application/json" \
     -d "{\"customerId\": \"$(uuidgen)\"}"

```

To perform the k6 test, you need to :
- Obtain the URL of the application and update the k6 target url in `script.js` 


- [Optional] If you want the test result to be exported into Prometheus :
- Obtain Prometheus URL :
```shell
minikube service prometheus --url
# http://127.0.0.1:57897
```
- Update the prometheus URL in `run-k6-test.sh` 
- Execute the script (use chmod if permission not yet given)
```shell
./run-k6-test.sh
```

