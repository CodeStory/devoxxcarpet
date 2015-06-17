/*
Copyright 2014 Google Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

var base = "http://localhost:8001/api/v1beta3/";

var updateServer = function($http, server) {
  $http.get(base + "namespaces/default/pods/" + server.podName)
    .success(function(data) {
      console.log(data);
      server.labels = data.metadata.labels;
      server.host = data.spec.host.split('.')[0];
      server.status = data.status.phase;
      server.dockerImage = data.status.containerStatuses[0].image;
    })
    .error(function(data) {
      console.log(data);
    });
};

var updateData = function($scope, $http) {
  var servers = $scope.servers;
  for (var i = 0; i < servers.length; ++i) {
    var server = servers[i];
    updateServer($http, server);
  }
};

var ButtonsCtrl = function ($scope, $http, $interval) {
  $scope.servers = [];
  update($scope, $http);
  $interval(angular.bind({}, update, $scope, $http), 2000);
};

var getServer = function($scope, name) {
  var servers = $scope.servers;
  for (var i = 0; i < servers.length; ++i) {
    if (servers[i].podName == name) {
      return servers[i];
    }
  }
  return null;
};

var isUpdateDemoPod = function(pod) {
  //return pod.metadata && pod.metadata.labels && pod.metadata.labels.carpet == "devoxxUK";
  return true
};

var update = function($scope, $http) {
  $http.get(base + "namespaces/default/pods")
    .success(function(data) {
      console.log(data);
      var newServers = [];
      for (var i = 0; i < data.items.length; ++i) {
        var pod = data.items[i];
        if (!isUpdateDemoPod(pod)) {
          continue;
        }
        var server = getServer($scope, pod.metadata.name);
        if (server == null) {
          server = { "podName": pod.metadata.name };
        }
        newServers.push(server);
      }
      $scope.servers = newServers;
      updateData($scope, $http);
    })
    .error(function(data) {
      console.log("ERROR: " + data);
    })
};
