appengine = require('appengine')
express = require('express')

app = express()

app.use(appengine.middleware.base)

app.get '/_ah/health', (req, res) ->
  res.set('Content-Type', 'text/plain')
  res.send(200, 'ok')

app.get '/_ah/start', (req, res) ->
  res.set('Content-Type', 'text/plain')
  res.send(200, 'ok')

app.get '/_ah/stop', (req, res) ->
  res.set('Content-Type', 'text/plain')
  res.send(200, 'ok')
  process.exit()

app.get '/carpets/match', (req, res) ->
  carpets = [
    {index: 1, score: 100, votes: 10}
    {index: 2, score: 200, votes: 5}
  ]
  res.send(JSON.stringify(carpets))

app.get '/carpets/top', (req, res) ->
  top =
    1: {rank: 1, carpet: {index: 1, score: 100, votes: 10}}
    2: {rank: 2, carpet: {index: 2, score: 100, votes: 10}}
    3: {rank: 3, carpet: {index: 3, score: 100, votes: 10}}
    4: {rank: 4, carpet: {index: 4, score: 100, votes: 10}}
    5: {rank: 5, carpet: {index: 5, score: 100, votes: 10}}
  res.send(JSON.stringify(top))

app.post '/votes/:winner/:looser', (req, res) ->
  res.send('')

app.use(express.static(__dirname + '/app'))

app.listen(8080)
