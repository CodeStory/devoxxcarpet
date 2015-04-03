var app = angular.module('app', []).controller('homeController', function ($scope, $http, $timeout) {
    console.log('hello');
    $scope.prefer_first = function () {
        return $http.post("/votes/" + $scope.left.index + "/" + $scope.right.index).success(function () {
            return $scope.settle_match('winner', 'looser');
        });
    };
    $scope.prefer_second = function () {
        return $http.post("/votes/" + $scope.right.index + "/" + $scope.left.index).success(function () {
            return $scope.settle_match('looser', 'winner');
        });
    };
    $scope.refresh = function () {
        $http.get('/carpets/match').success(function (data) {
            return $scope.left = data[0], $scope.right = data[1], data;
        });
        return $http.get('/carpets/top').success(function (data) {
            return $scope.top = data;
        });
    };
    $scope.settle_match = function (left_status, right_status) {
        $scope.left.status = left_status;
        $scope.right.status = right_status;
        return $scope.refresh_after_animation();
    };
    $scope.refresh_after_animation = function () {
        return $timeout($scope.refresh, 1500);
    };
    return $scope.refresh();
});