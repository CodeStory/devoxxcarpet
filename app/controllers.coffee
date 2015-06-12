app = angular.module 'app', []

.service 'Votes', ($http) ->
  match: ->
    $http.get "/carpets/match", cache: false
  carpets: ->
    $http.get "/carpets", cache: false
  vote: (winner, looser) ->
    $http.post "/votes/#{winner}/#{looser}"

.controller 'HomeController', class
  constructor: (@Votes) ->
    @refresh()

  preferFirst: ->
    @Votes.vote(@left.index, @right.index).success =>
      @refresh()

  preferSecond: ->
    @Votes.vote(@right.index, @left.index).success =>
      @refresh()

  refresh: ->
    @Votes.match().success (data) =>
      [@left, @right] = data

    @Votes.carpets().success (carpets) =>
      carpets.sort (l, r) -> r.score - l.score
      carpet.rank = i for carpet,i in carpets
      @top = carpets
