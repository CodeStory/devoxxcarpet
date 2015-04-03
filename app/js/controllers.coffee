app = angular.module 'app', []

.controller 'homeController', ($scope, $http, $timeout) ->
  console.log 'hello'
  $scope.prefer_first = ->
    $http.post("/votes/#{$scope.left.index}/#{$scope.right.index}").success ->
      $scope.settle_match('winner', 'looser')

  $scope.prefer_second = ->
    $http.post("/votes/#{$scope.right.index}/#{$scope.left.index}").success ->
      $scope.settle_match('looser', 'winner')

  $scope.refresh = ->
    $http.get('/carpets/match').success (data) ->
      [$scope.left, $scope.right] = data

    $http.get('/carpets/top').success (data) ->
      $scope.top = data

  $scope.settle_match = (left_status, right_status) ->
    $scope.left.status = left_status
    $scope.right.status = right_status
    $scope.refresh_after_animation()

  $scope.refresh_after_animation = ->
    $timeout($scope.refresh, 1500)

  $scope.refresh()
