# SpringBootRedisLeaderBoard
# READY-TO-RUN PROJECT


# After cloning the repo and opening in IntelliJ, please find LeaderBoardApplication and run the main method. The application will be up and running at localhost:8080

This project uses Springboot and Redis to persist and rander the leader data.

For having a dockerized redis running in your local env, please use the docker-compose.yml or run below command:

# docker run -dp 6379:6379 --name some-redis -d redis 


Two main APIs are implemented, one is to return all the leaders' data in descending order based on scores, and the other one is to return specific leader and the "neighbors" within the range based on the score in Json format. 
Pagination is also implemented.

Example API invocation:

@PostMapping("/leaderBoard/{username}/{score}")

Reqeust: POST http://localhost:8080/api/leaderBoard/testuser/2

Response: {
    "username": "testuser",
    "index": 0,
    "score": 2
}




@GetMapping("/leaderBoard/all/{pageNumber}/{pageSize}")

Reqeust: GET http://localhost:8080/api/leaderBoard/all/1/50

Response:
{
"leaderBoardEntries": [
{
"username": "testuse32",
"index": 0,
"score": 108745
},
{
"username": "testuser3",
"index": 1,
"score": 10900
},
{
"username": "testuse230",
"index": 2,
"score": 9294
},
{
"username": "testuse2r",
"index": 3,
"score": 1087
},
{
"username": "testuse20",
"index": 4,
"score": 994
},
{
"username": "testuse1",
"index": 5,
"score": 99
},
{
"username": "testuser",
"index": 6,
"score": 10
},
{
"username": "testuser3",
"index": 7,
"score": 5
},
{
"username": "testuserfdef",
"index": 8,
"score": 2
}
],
"page": 1,
"count": 50
}


@GetMapping("/leaderBoard/all/{pageNumber}/{pageSize}") with pagination

Reqeust: GET http://localhost:8080/api/leaderBoard/all/1/3

Response:
{
"leaderBoardEntries": [
{
"username": "testuse32",
"index": 0,
"score": 108745
},
{
"username": "testuser3",
"index": 1,
"score": 10900
},
{
"username": "testuse230",
"index": 2,
"score": 9294
}
],
"page": 1,
"count": 3
}



Reqeust: GET http://localhost:8080/api/leaderBoard/all/2/3

Response:
{
"leaderBoardEntries": [
{
"username": "testuse2r",
"index": 3,
"score": 1087
},
{
"username": "testuse20",
"index": 4,
"score": 994
},
{
"username": "testuse1",
"index": 5,
"score": 99
}
],
"page": 2,
"count": 3
}


Reqeust: GET http://localhost:8080/api/leaderBoard/all/3/3

Response:
{
"leaderBoardEntries": [
{
"username": "testuser",
"index": 6,
"score": 10
},
{
"username": "testuser3",
"index": 7,
"score": 5
},
{
"username": "testuserfdef",
"index": 8,
"score": 2
}
],
"page": 3,
"count": 3
}


 @GetMapping(value ="/leaderBoard/{username}/{pageSize}"

Request: GET http://localhost:8080/api/leaderBoard/testuser3/2

Response:[
    {
        "username": "testuser3",
        "index": 1,
        "score": 10900
    },
    {
        "username": "testuse230",
        "index": 2,
        "score": 9294
    }
]




 @GetMapping(value ="/leaderBoard/{username}/{pageSize}"

Reqeust: GET http://localhost:8080/api/leaderBoard/testuser3/5

Response:[
    {
        "username": "testuse32",
        "index": 0,
        "score": 108745
    },
    {
        "username": "testuser3",
        "index": 1,
        "score": 10900
    },
    {
        "username": "testuse230",
        "index": 2,
        "score": 9294
    },
    {
        "username": "testuse2r",
        "index": 3,
        "score": 1087
    },
    {
        "username": "testuse20",
        "index": 4,
        "score": 994
    }
]


