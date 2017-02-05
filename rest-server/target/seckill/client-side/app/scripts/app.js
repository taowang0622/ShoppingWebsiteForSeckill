'use strict';

angular.module('seckill', ['ui.router', 'ngResource'])
  .config(function ($stateProvider, $urlRouterProvider, $qProvider) {
    $stateProvider
      .state('list', {
        url: '/',
        views: {
          'itemList': {
            templateUrl: 'views/list.html',
            controller: 'ListController'
          }
        }
      });

    //redirecting to '/' page!!!
    $urlRouterProvider.otherwise('/');

    // $qProvider.errorOnUnhandledRejections(false);
  });
