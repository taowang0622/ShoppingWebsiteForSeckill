'use strict';

/**
 * Created by taowang on 2/4/2017.
 */
angular.module('seckill')
    .controller('ListController', ['$scope', '$state', '$rootScope', '$cookies', 'ngDialog', 'itemListFactory', function ($scope, $state, $rootScope, $cookies, ngDialog, itemListFactory) {
        $scope.showList = false;
        $scope.message = "Loading.....";

        itemListFactory.get(
            //if request is sent successfully
            function (response) {
                if (response.successful) {
                    $scope.items = response.data;
                    $scope.showList = true;
                }
                else {
                    $scope.message = response.error;
                }
            },

            //if request sending fails
            function (response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            });

        $scope.showDetail = function (itemId) {
            $scope.id = itemId; //Sharing the data
            if (!$cookies.getObject('userPhone')) {
                //Create a pop-up!
                ngDialog.open({
                    name: 'loginDialog',
                    template: 'views/login.html',
                    scope: $scope,
                    //Above pair would pass a scope object into this controller as parent controller
                    controller: 'LoginController',
                    className: 'ngdialog-theme-default',
                    closeByDocument: false,
                    closeByEscape: false
                });
            }
            else {
                //redirecting
                $state.go('list.detail', {id: itemId});
            }
        }

    }])
    .controller('DetailController', ['$scope', '$stateParams', 'itemDetailFactory', 'timeFactory', '$interval', 'executionFactory', 'exposerFactory' ,function ($scope, $stateParams, itemDetailFactory, timeFactory, $interval, executionFactory, exposerFactory) {
        $scope.showDetail = false; //Show all details
        $scope.message = 'Loading.....';
        $scope.over = false;  //Indicating if the seckill for a particular item has been over
        $scope.started = false;
        $scope.countingDown = false;
        $scope.exposed = false;
        $scope.purchased = false;
        $scope.successfulSeckill = false;
        var currentTime = null; //Storing the current server time

        var stop;
        var countDownStop = function () {
            $interval.cancel(stop);
            //Show details!!!
            $scope.countingDown = false;
            $scope.started = true;
            exposeSeckill();
        };

        var countdownStart = function (startTime, endTime) {
            var interval = endTime - startTime;
            var days, hours, minutes, seconds;
            stop = $interval(function () {
                if (interval <= 0) countDownStop();
                days = interval / (3600000 * 24);
                hours = (interval % (3600000 * 24)) / 3600000;
                minutes = ((interval % (3600000 * 24)) % 3600000) / 60000;
                seconds = (((interval % (3600000 * 24)) % 3600000) % 60000) / 1000;
                interval -= 1000;
                $scope.countDown = parseInt(days) + ' days, ' + parseInt(hours) + ' hours, ' + parseInt(minutes) + ' minutes, ' + parseInt(seconds) + ' seconds'
            }, 1000)
        };

        var exposeSeckill = function () {
            //non-GET "class" actions: Resource.action([parameters], postData, [success], [error])
            //Be aware of the second parameter!!
            exposerFactory.save({id: $stateParams.id}, null,  //No contents to be passed, so it's null!!!
                function (response) {
                    if (response.successful) {
                        $scope.md5 = response.data.md5;
                        //show button
                        $scope.exposed = true;
                        //Create a function object for executing seckill
                        $scope.executeSeckill = function () {
                            executionFactory.postReq({id: $stateParams.id, md5: $scope.md5}, null, //No contents to be passed, so it's null!!!
                                function (response) {
                                    if (response.successful) {
                                        if (response.data.stateCode === 1) {//Execution succeeded
                                            $scope.executionStateInfo = response.data.stateInfo;
                                            $scope.purchased = true;
                                            $scope.successfulSeckill = true;
                                        }
                                        else if (response.data.stateCode === 0) { //Seckill ended
                                            $scope.over = true;
                                            $scope.started = false;
                                            $scope.exposed = false;
                                        }
                                        else if (response.data.stateCode === -1) {//Seckill repeated
                                            $scope.executionStateInfo = response.data.stateInfo;
                                            $scope.purchased = true;
                                            $scope.successfulSeckill = false;
                                        }
                                        else if (response.data.stateCode === -3) {//Data modified
                                            $scope.executionStateInfo = response.data.stateInfo;
                                            $scope.purchased = true;
                                            $scope.successfulSeckill = false;
                                        }
                                    } else {
                                        $scope.showDetail = false;
                                        $scope.message = response.error;
                                    }
                                },
                                function () {
                                    $scope.showDetail = false;
                                    $scope.message = "Error: " + response.status + " " + response.statusText;
                                })
                        }
                    } else {
                        $scope.showDetail = false;
                        $scope.message = response.error;
                    }
                },
                function (response) {
                    $scope.showDetail = false;
                    $scope.message = "Error: " + response.status + " " + response.statusText;
                })
        };

        //Obtaining the current server time and determine if the seckill has ended
        timeFactory.get(
            function (response) {
                if (response.successful) {
                    currentTime = response['data'];
                    //Gaining the item detail
                    itemDetailFactory.get({id: $stateParams.id},
                        function (response) {
                            if (response.successful) {
                                $scope.item = response['data'];
                                //seckill has ended
                                if (currentTime >= $scope.item.endTime) {
                                    $scope.over = true;
                                }
                                //has started
                                else if (currentTime >= $scope.item.startTime) {
                                    $scope.started = true;
                                    exposeSeckill();
                                }
                                //hasn't started yet
                                else {
                                    countdownStart(currentTime, $scope.item.startTime);
                                    $scope.countingDown = true;
                                }
                                $scope.showDetail = true; //Show all the details
                            } else {
                                $scope.message = response.error;
                            }
                        },
                        function (response) {
                            $scope.message = "Error: " + response.status + " " + response.statusText;
                        })
                } else {
                    $scope.message = response.error;
                }
            },
            function (response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            });

    }])
    .controller('LoginController', ['$scope', '$cookies', '$state', 'ngDialog', function ($scope, $cookies, $state, ngDialog) {
        //TODO validating the user input
        $scope.doLogin = function () {
            if (!$cookies.get('userPhone')) {
                $cookies.put('userPhone', $scope.userPhone);
                $state.go('list.detail', {id: $scope.$parent.id}); //Redirecting
                ngDialog.close('loginDialog'); //close the login dialog
            }
        };
    }]);
