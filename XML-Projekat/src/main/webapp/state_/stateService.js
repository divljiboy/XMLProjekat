(function(angular){
    
    angular.module("xmlApp").service("stateService",['$http', function($http){
        
        return {
            getState : function(onSuccess,onError){
                 var req = {
                     method: 'GET',
                     url: 'korisnik/state',
                     headers: {
                     'Content-Type': 'application/json'
                             }
                    
                }
                    $http(req).then(onSuccess, onError);
                },
            setState : function(state,onSuccess,onError){
                var req = {
                     method: 'POST',
                     url: '/korisnik/state',
                     headers: {
                     'Content-Type': 'application/json'
                             },
                    data: state
                    
                }
                    $http(req).then(onSuccess, onError);
            }
        }
        
    }]);
})(angular);