/**
 * Created by David on 6/9/2016.
 */


(function(angular){

        angular.module("xmlApp").service('authService',['$http', '$rootScope', function($http,$rootScope){
                return {
                        post: function (korisnik, onSuccess, onError) {
                                var req = {
                                        method: 'POST',
                                        url: '/korisnik/dodaj',
                                        headers: {
                                                'Content-Type': 'application/json'
                                        },
                                        data: korisnik

                                }
                                $http(req).then(onSuccess, onError);
                        },
                        
                        
                        login: function (credentials, onSuccess, onError) {
                                $http.post('/korisnik/login', credentials).success(onSuccess,onError);
                        },
                        
                        logout: function () {
                                localStorage.removeItem('token');
                                $rootScope.authenticated = false;

                        },

                        checkUser: function(){
                                $http.get('/korisnik/current').success(function (user) {
                                        if(user.role === 'Gradjanin'){
                                                //$scope.authenticated = true;
                                                //$scope.username = user.username;
                                                //nesto
                                        }
                                });
                        }
                }
        }]);

})(angular);
