docker run -d -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 --network="test-network" rabbitmq:3-management

docker build .\service1\
Successfully built f8cc760ef24a

docker run -d --name api-gateway-service --network="test-network" -p 4848:4848 -p 8080:8080 f8cc760ef24a

docker run -d --name service1 --network="test-network" -p 4801:4848 -p 8001:8080 f8cc760ef24a

docker run -d --name service2 --network="test-network" -p 4802:4848 -p 8002:8080 f8cc760ef24a

docker container ps
