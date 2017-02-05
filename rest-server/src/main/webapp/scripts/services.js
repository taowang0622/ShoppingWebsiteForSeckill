/**
 * Created by taowang on 2/4/2017.
 */
angular.module('seckill')
  .constant('baseURL', 'http://localhost:8080/seckill')
  .factory('itemListFactory', ['$resource', 'baseURL', function ($resource, baseURL) {
    return $resource(baseURL + '/list', null);
  }]);
