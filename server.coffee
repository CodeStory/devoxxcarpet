appengine = require 'appengine'
express = require 'express'
fs = require 'fs'
coffee = require 'coffee-script'
less = require 'less'

CARPET_COUNT = 12
playedPerIndex = (0 for i in [0...CARPET_COUNT])
scorePerIndex = (1000 for i in [0...CARPET_COUNT])
votesPerIndex = (0 for i in [0...CARPET_COUNT])

app = express()

app.use(express.static(__dirname + '/app'))
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

app.get '/version', (req, res) ->
  res.set('Content-Type', 'text/plain')
  res.send("Node")

app.get '/carpets/match', (req, res) ->
  [left, right] = [Math.floor(Math.random() * CARPET_COUNT), Math.floor(Math.random() * CARPET_COUNT)] while (left is right)

  carpets = [
    {index: left, score: scorePerIndex[left], votes: votesPerIndex[left]}
    {index: right, score: scorePerIndex[right], votes: votesPerIndex[right]}
  ]
  res.send(JSON.stringify(carpets))

app.get '/carpets/top', (req, res) ->
  carpets = ({index: i, votes: votesPerIndex[i]} for i in [0...CARPET_COUNT])
  carpets.sort (l,r) -> r.votes - l.votes

  top = {}
  for rank in [0...CARPET_COUNT]
    carpet = carpets[rank]
    top[carpet.index] = {rank: rank, carpet: carpet}

  res.send(JSON.stringify(top))

app.get '/js/controllers.js', (req, res) ->
  cs = fs.readFileSync("#{__dirname}/app/js/controllers.coffee", 'UTF-8')
  js = coffee.compile(cs)
  res.header 'Content-Type', 'application/x-javascript'
  res.send js

app.get '/css/style.css', (req, res) ->
  source = fs.readFileSync("#{__dirname}/app/css/style.less", 'UTF-8')
  less.render source, (e, output) ->
    res.header 'Content-Type', 'text/css'
    res.send output.css

k = (score, played) ->
  #return played < 30 ? 25 : score < 2400 ? 15 : 10;
  if played < 30 then 25 else (if score < 2400 then 15 else 10)

app.post '/votes/:winner/:looser', (req, res) ->
  winner = +req.params.winner
  looser = +req.params.looser
  score1 = scorePerIndex[winner]
  score2 = scorePerIndex[looser]

  d = Math.min(400, Math.abs(score1 - score2))
  p = 1.0 / (1.0 + Math.pow(10, -d / 400.0))

  k1 = k(score1, ++playedPerIndex[winner])
  k2 = k(score2, ++playedPerIndex[looser])

  r1 = Math.round(score1 + (k1 * (1 - p)))
  r2 = Math.round(score2 + (k2 * (0 - p)))

  votesPerIndex[winner]++
  scorePerIndex[winner] = r1
  scorePerIndex[looser] = r2
  res.send('')

app.listen(8080)

