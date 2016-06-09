/**
 * Created by David on 6/9/2016.
 */


(function(angular){

        angular.module("xmlApp").service('authService', function($http){
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
                        }


                }
        });

})(angular);
