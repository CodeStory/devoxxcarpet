    app = angular.module 'app', []

Random match between two carpets.

    .service 'Match', ->
      random: (count) ->
        [left, right] = [Math.floor(Math.random() * count), Math.floor(Math.random() * count)] while (left is right)
        [left, right]

Retrieve all the carpets or Vote for a carpet against another.

    .service 'Votes', ($http) ->
      carpets: ->
        $http.get "/carpets", cache: false
      vote: (winner, looser) ->
        $http.post "/votes/#{winner}/#{looser}"

Main controller.

    .controller 'HomeController', class
      constructor: (@Votes, @Match) ->
        @refresh()

The First carpet won. Let's post the vote, refresh the leaderboard and show a new choice.

      preferFirst: ->
        @Votes.vote(@left, @right).success =>
          @refresh()

The Second carpet won.

      preferSecond: ->
        @Votes.vote(@right, @left).success =>
          @refresh()

Get all the carpets with their votes, sort by number of votes to compute the ranks.

      refresh: ->
        @Votes.carpets().success (carpets) =>
          carpets.sort (l, r) -> r.score - l.score
          carpet.rank = i for carpet,i in carpets
          @top = carpets

          [@left, @right] = @Match.random(@top.length)
