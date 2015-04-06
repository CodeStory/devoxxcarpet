CARPET_COUNT = 12

app = angular.module 'app', ['firebase']

.factory 'Votes', ($firebaseArray) ->
  $firebaseArray(new Firebase('https://popping-fire-1784.firebaseio.com/Votes'))

.service 'Rankings', class
  constructor: ->
    console.log 'Rankings'
    @playedPerIndex = (0 for i in [0...CARPET_COUNT])
    @scorePerIndex = (1000 for i in [0...CARPET_COUNT])
    @votesPerIndex = (0 for i in [0...CARPET_COUNT])

  k: (score, played) ->
    if played < 30 then 25 else (if score < 2400 then 15 else 10)

  vote: (winner, looser) ->
    console.log "#{winner} > #{looser}"
    score1 = @scorePerIndex[winner]
    score2 = @scorePerIndex[looser]

    d = Math.min(400, Math.abs(score1 - score2))
    p = 1.0 / (1.0 + Math.pow(10, -d / 400.0))

    k1 = @k(score1, ++@playedPerIndex[winner])
    k2 = @k(score2, ++@playedPerIndex[looser])

    r1 = Math.round(score1 + (k1 * (1 - p)))
    r2 = Math.round(score2 + (k2 * (0 - p)))

    @votesPerIndex[winner]++
    @scorePerIndex[winner] = r1
    @scorePerIndex[looser] = r2

.controller 'HomeController', class
  constructor: (@$scope, @Votes, @Rankings) ->
    @updateLeaderboard()
    @match()
    @Votes.$watch (event) =>
      match = @Votes.$getRecord(event.key)
      @Rankings.vote(match.winner, match.looser)
      @updateLeaderboard()

  refresh: ->
    for vote in @Votes
      console vote

  updateLeaderboard: ->
    carpets = ({index: i, votes: @Rankings.votesPerIndex[i]} for i in [0...CARPET_COUNT])
    carpets.sort (l,r) -> r.votes - l.votes

    @top = {}
    for rank in [0...CARPET_COUNT]
      carpet = carpets[rank]
      @top[carpet.index] = {rank: rank, carpet: carpet}

  match: ->
    [left, right] = [Math.floor(Math.random() * CARPET_COUNT), Math.floor(Math.random() * CARPET_COUNT)] while (left is right)
    [@left, @right] = [left, right]
    console.log "#{@left} vs #{@right}"

  preferFirst: ->
    @vote(@left, @right)

  preferSecond: ->
    @vote(@right, @left)

  vote: (winner, looser) ->
    @Votes.$add
      winner: winner
      looser: looser
    @match()