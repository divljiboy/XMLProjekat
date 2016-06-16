(function(angular){

    //angular.module('xmlApp')
    //    factory('authInterceptor',['$q', function($q) {
    //    return {
    //        request: function(config) {
    //            console.log(config);
    //            var authToken = localStorage.getItem('token');
    //            if (authToken) {
    //                config.headers['X-AUTH-TOKEN'] = authToken;
    //            }
    //            return config;
    //        },
    //        responseError: function(error) {
    //            if (error.status === 401 || error.status === 403) {
    //                localStorage.removeItem('token');
    //            }
    //            return $q.reject(error);
    //        }
    //    }
    //}]);
    //
    //angular.module('xmlApp').config(['$httpProvider', function ($httpProvider) {
    //        $httpProvider.interceptors.push('authInterceptor');
    //    }]);

});