'use strict';

angular.module('seckill')
    .constant('baseURL', 'http://ec2-35-183-5-195.ca-central-1.compute.amazonaws.com/seckill')
    .factory('itemListFactory', ['$resource', 'baseURL', function ($resource, baseURL) {
        return $resource(baseURL + '/list', null);
    }])
    .factory('itemDetailFactory', ['$resource', 'baseURL', function ($resource, baseURL) {
        return $resource(baseURL + '/:id/detail', null);
    }])
    .factory('timeFactory', ['$resource', 'baseURL', function ($resource, baseURL) {
        return $resource(baseURL + '/time/now');
    }])
    .factory('exposerFactory', ['$resource', 'baseURL', function ($resource, baseURL) {
        return $resource(baseURL + '/:id/exposer');
    }])
    .factory('executionFactory', ['$resource', 'baseURL', '$cookies', function ($resource, baseURL, $cookies) {
        return $resource(baseURL + '/:id/:md5/execution', null, {
            'postReq': {
                method: 'POST',
                withCredentials: true
            }
        });
    }]);

