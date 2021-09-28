# GoSecureSEGAssignment

## Setup Instructions

### 1) Build the writecontainer module:
There are two options to build the writecontainer:
#### Option 1) build with tests (requires a running Redis image):
```
docker pull redis
docker run --name personDirectory -p 6379:6379 redis
```
While it's running:
```
cd writecontainer
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

#### Option 2) build and skip tests:
```
cd writecontainer
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -Dmaven.test.skip=true
```

### 2) Build and run the docker images:
```
docker-compose up
```

### 3) Access in a browser.

The readcontainer module's HTTP Server will respond if you visit:
0.0.0.0:8080/people
