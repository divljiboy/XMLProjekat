/**
 * Created by David on 6/13/2016.
 */


(function (angular) {

    angular.module("xmlApp").service("glasanjeService",['$http',function($http) {

        return {

            glasaj : function(entity, onSuccess, onError)
            {
                req = {
                    method : "POST",
                    url : "amandman/glasaj",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: entity
                }
                $http(req).then(onSuccess,onError);
            }
        }
    }]);


})(angular);