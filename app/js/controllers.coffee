app = angular.module 'app', []

.service 'Votes', ($http) ->
  match: ->
    $http.get "/carpets/match", cache: false
  top: ->
    $http.get "/carpets/top", cache: false
  vote: (winner, looser) ->
    $http.post "/votes/#{winner}/#{looser}"

.controller 'HomeController', class
  constructor: (@$timeout, @Votes) ->
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

    @Votes.top().success (data) =>
      @top = data
