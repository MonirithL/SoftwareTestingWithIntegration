This project is a contribution of:

- Lim Ankim
- Leng Monirith
- Teng RithTengRatanak

# Get Started

- copy and paste the application.properties (remove the .) and add your own port, db_name, username, password

## Requirements

(Part of FullStack App)
This app is a **spring boot** web api that connects to **MYSQL** that allows users

the **CRUD** requirements:

- User(username, email, password)
- Login()
- Game(user_id, word, score, dateString)
- Leaderboard(user_id, total_score, last_updated)

secondary tables:

- _guess_ for game

API Integration:

- Free Dictionary API (need word and definition)

## 1. Guidelines for dev

1. Backend Controller Guide

- [x] post("auth/register") - user register
  [email, username, password]

- [x] post("auth/login") - user login
  [email, password]

- [x] get("/api/authCheck) - protect user from nav if not logged in
  [http cookie]

- [x] post("/auth/logout) = clear http cookie for /api

put("/games") - add game info
[new Game()]

get("/games") - get games history
[List<Game> games]

get("/leaderboard") - get all leaderboards with user
[user_id, score, date_updated. user_id->username]

put("/leaderboard) - add or increase users score on the leaderboard
[user_id, score]

- [x] get("/word") - get a word from an api with definition
  [UNSURE BUT JSON word, definition]

## 2. Backend Function guide

- [x] addUser(username, email, password)->User
  [Create user in db]

- [x] login(email, password)->bool|jwt|session
  [Read db and compare hash]

- [x] login-check(req, res)->status 200 | json ok
  [Read the cookie, check if user exists]

addGame(game)-> Game
[Create game in db, belonging to user_id]

getGames()-> List<Games>
[Read db and get a list of Games history by user]

addScoreToLeaderboard(leaderboard)->leaderboard
[Create leaderboard score in db]

getLeaderboard()->List<Leaderboard>
[Get leaderboard score and also join user_id with username and ORDER DESC, LIMIT 50]

- [x] getWord()-> Word{word:"word", definition:"definition"}

[Currently have options from strategy below (after function: read db for used word today, get from game):

1.  Save the word in db and select random
2.  use another api live and call the dictionary to get definition until true
3.  same approach as 2 but instead we start by getting 10 words and cache in map{key,value} for use
    ]

## 3. Testing

_(JWT AUTH FILTER MAY BE REMOVED BEFORE TESTING TO EVAL BEHAIVOR WITHOUT AUTH, it doesnt need auth to function anyway, auth just protects!)_

### Unit testing

#### Game

- addGame: eval game is not null
- getLeaderBoard: eval leaderboard is not null

#### User

- addUser: eval user is inserted
- getUserByID: eval user is returned
- checkUserLogin: check if Some User

#### JWT utils(OPTIONAL)

- generate token: check if return a token

### Integration testing with Mockito

#### User Controller

- get /{id}: confirm returns a user

#### Auth Controller

- post /register: check if name empty, malformed, email empty, email malformed, insecure password and return correct or not
- post /login: check if can login

#### Leaderboard Controller

- get leaderboard: check returns a valid leaderboard

#### Word Controller

- get word: check if return a valid word

#### Game Controller

- get start: check if it return res
- post guess: check if result as expected
- post save: check if it returned a game

### Manual Testing

- Set cookie: check cookie is added after login
- logout: check cookie is removed after logged out
- authCheck: check user can access all protected route by going to /api/authCheck to get string

### UI testing

- register
- login
- start game
- guess word
- finish/save game
- view leaderboard
- logout
