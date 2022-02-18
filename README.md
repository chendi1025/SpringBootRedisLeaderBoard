# SpringBootRedisLeaderBoard
# Java

This project uses Springboot and Redis to persist and rander the leader data.

For having a dockerized redis running in your local env, please use the docker-compose.yml or run below command:
docker run -dp 6379:6379 --name some-redis -d redis 

After clone the repo, please find LeaderBoardApplication and run the main method. The application will be up and running at port 8080

Two main APIs are implemented to return all the leader data and specific leaders with in the range based on the score in Json format. 
Pagination is also implemented.

Example API invacation:

@PostMapping("/leaderBoard/{username}/{score}")
Reqeust: POST http://localhost:8080/api/leaderBoard/testuser/2
Response: {
    "username": "testuser",
    "index": 0,
    "score": 2
}


@GetMapping("/leaderBoard/all/{pageNumber}/{pageSize}")
Reqeust: GET http://localhost:8080/api/leaderBoard/all/1/50
Response:{
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
            "username": "testuserfdef",
            "index": 7,
            "score": 2
        }
    ],
    "page": 1,
    "count": 50
}


@GetMapping("/leaderBoard/all/{pageNumber}/{pageSize}")
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



 @GetMapping(value ="/leaderBoard/{username}/{pageSize}"
Reqeust: GET http://localhost:8080/api/leaderBoard/testuser3/2
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
