#!/bin/bash

# build front-end code
cd ../../angular-app/
gulp
cd ../rest-server/docker/
# copy the dist to the server public folder
rm -rf ../src/main/webapp/dist/
cp -r ../../angular-app/dist/ ../src/main/webapp/dist/

# build back-end code
cd ..
mvn clean package
cd docker/

# deploy
docker rm -f seckill-tomcat-container
docker rm -f redis-container
docker rmi -f seckill-tomcat
docker build -t seckill-tomcat .
docker run --name redis-container -d redis redis-server
docker run --name seckill-tomcat-container -e JPDA_ADDRESS=8000 -e JPDA_TRANSPORT=dt_socket -p 8080:8080 -p 9000:8000 -d seckill-tomcat /usr/local/tomcat/bin/catalina.sh jpda run

# create a network to let containers to talk to each other
docker network rm seckill-network
docker network create seckill-network
docker network connect seckill-network redis-container
docker network connect seckill-network seckill-tomcat-container
