/**
 * Created by taowang on 2/4/2017.
 */
angular.module('seckill')
  .controller('ListController', ['$scope', 'itemListFactory', function ($scope, itemListFactory) {
    $scope.showList = true;
    $scope.message = "Loading.....";


    itemListFactory.get(
      //if request is sent successfully
      function (response) {
        if (response['successful']) {
          $scope.items = response['data'];
          $scope.showList = true;
        }
        else {
          $scope.message = response['error'];
        }
      },

      //if request sending fails
      function (response) {
        $scope.message = "Error: " + response.status + " " + response.statusText;
      });
  }]);
