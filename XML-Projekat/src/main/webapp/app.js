/**
 *
 */

(function(angular){

    angular.module("xmlApp",['ui.router', 'ui.bootstrap', 'ui.grid', 'ui.grid.selection','ngAnimate','ngAria','ngMaterial'])
        .config(['$stateProvider', '$urlRouterProvider','$httpProvider', function ($stateProvider, $urlRouterProvider,$httpProvider) {

            $urlRouterProvider.otherwise('/home');

            $stateProvider
                .state('home', {
                    url: '/home',
                    views: {
                        'navbar': {
                            templateUrl:'navbar/navbar.html'
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html'
                        }
                    }
                })

            $httpProvider.interceptors.push(['$q','$rootScope','$injector',function($q,$rootScope,$injector) {
                return {
                    request: function (config) {
                        var authToken = localStorage.getItem('token');
                        if (authToken) {
                            config.headers['X-AUTH-TOKEN'] = authToken;
                        }
                        //config.headers['X-AUTH-TOKEN'] = "eyJpbWUiOm51bGwsInByZXppbWUiOm51bGwsInVzZXJuYW1lIjpudWxsLCJwYXNzd29yZCI6bnVsbCwiZW1haWwiOm51bGwsInVsb2dhIjoiR3JhZGphbmluIiwic2FsdCI6bnVsbCwiaWQiOm51bGx9.NaoLx5lATxwbrhGBFRziMH0P8BPfH+XLkDQ1BHiRgwI=";
                        //console.log(config.headers['X-AUTH-TOKEN']);
                        return config;
                    },
                    responseError: function (error) {

                        if (error.status === 401 || error.status === 403) {
                            var stateService = $injector.get('$state');
                            localStorage.removeItem('token');
                            stateService.go('signIn');
                        }
                        return $q.reject(error);
                    }
                }
            }]);
        }]);

})(angular);