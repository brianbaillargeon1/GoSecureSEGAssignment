# GoSecureSEGAssignment

Build writecontainer

Build readcontainer(?)

docker-compose up


/** ------ ignore ----------*/

Pull in the Docker Redis container:
docker pull redis

docker run --name personDirectory -p 6379:6379 redis

/* This might conflict with later connections */
Ensure you can connect with redis-cli:
>docker exec -it personDirectory redis-cli
>PING
PONG
>SADD person '{"name":"John Doe", "address":"123 Example Street", "phone":"123-456-7890", "email":"jdoe@example.com"}'
>SMEMBERS person
1) "{\"name\":\"John Doe\", \"address\":\"123 Example Street\", \"phone\":\"123-456-7890\", \"email\":\"jdoe@example.com\"}"

Build the writecontainer module using:
cd writecontainer
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
docker build -t writecontainer
docker run writecontainer


/** ------ /ignore ----------*/
