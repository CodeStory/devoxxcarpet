app = angular.module 'app', []

.controller 'HomeController', class
  constructor: (@$http, @$timeout) ->
    @refresh()

  prefer_first: =>
    @$http.post("/votes/#{@left.index}/#{@right.index}").success =>
      @settle_match('winner', 'looser')

  prefer_second: =>
    @$http.post("/votes/#{@right.index}/#{@left.index}").success =>
      @settle_match('looser', 'winner')

  refresh: =>
    @$http.get('/carpets/match').success (data) =>
      [@left, @right] = data

    @$http.get('/carpets/top').success (data) =>
      @top = data

  settle_match: (left_status, right_status) =>
    @left.status = left_status
    @right.status = right_status
    @refresh_after_animation()

  refresh_after_animation: =>
    @$timeout(@refresh, 1500)

