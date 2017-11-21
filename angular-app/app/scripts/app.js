'use strict';

angular.module('seckill', ['ui.router', 'ngResource', 'ngCookies', 'ngDialog'])  //importing the third-party modules to use
  .config(function ($stateProvider, $urlRouterProvider, $cookiesProvider) {
    $cookiesProvider.defaults.path = '/';
    // $cookiesProvider.defaults.domain = 'localhost';
    $cookiesProvider.defaults.domain = 'ec2-35-183-5-195.ca-central-1.compute.amazonaws.com';


    $stateProvider
      .state('list', {
        url: '/',
        views: {
          'header': {
            templateUrl: 'views/header.html'
          },
          'content': {
            templateUrl: 'views/list.html',
            controller: 'ListController'
          }
        }
      })
      .state('list.detail', {
        url: ':id/detail',
        views: {
          'content@': {
            templateUrl: 'views/detail.html',
            controller: 'DetailController'
          }
        }
      })
      .state('list.login', {
        url: 'login',
        views: {
          'content@': {
            templateUrl: 'views/login.html',
            controller: 'LoginController'
          }
        }
      });

    //redirecting to '/' page!!!
    $urlRouterProvider.otherwise('/');
  });
