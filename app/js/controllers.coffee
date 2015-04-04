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
      @settleMatch('winner', 'looser')

  preferSecond: ->
    @Votes.vote(@right.index, @left.index).success =>
      @settleMatch('looser', 'winner')

  refresh: ->
    @Votes.match().success (data) =>
      [@left, @right] = data

    @Votes.top().success (data) =>
      @top = data

  settleMatch: (left_status, right_status) ->
    @left.status = left_status
    @right.status = right_status
    @refresh_after_animation()

  refresh_after_animation: ->
    @$timeout =>
      @refresh()
    , 1000

