/**
 * Created by David on 6/9/2016.
 */


(function(angular){

    angular.module("xmlApp").config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        $stateProvider
            .state('signIn', {
                url: '/signin',
                views: {
                    "content":{
                        templateUrl:'auth_/signin_/signIn.html',
                        controller: 'signInController'
                    }
                }
            })
            .state('signUp', {
                url: '/signup',
                views: {
                    "content":{
                        templateUrl:'auth_/signup_/signUp.html',
                        controller: 'signUpController'
                    }
                }

            });

    }]);

})(angular);