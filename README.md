# SpringBootRedisLeaderBoard
# Java

This project uses Springboot and Redis to persist and rander the leader data.

For having a dockerized redis running in your local env, please use the docker-compose.yml or run below command:
docker run -dp 6379:6379 --name some-redis -d redis 

After clone the repo, please find LeaderBoardApplication and run the main method. The application will be up and running at port 8080

Two main APIs are implemented to return all the leader data and specific leaders with in the range based on the score in Json format. 
Pagination is also implemented.

