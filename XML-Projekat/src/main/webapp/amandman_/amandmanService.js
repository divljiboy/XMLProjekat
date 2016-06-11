(function(angular){
    
    angular.module("xmlApp").service("amandmanService",'$http' [function($http){
        
        return  {
                
            
                // predlaganje amandmana
                create: function(aktId, entity,onSuccess,onError){
                    var req = {
                        method: 'POST',
                        url: 'amandman/'+ aktId ,
                        headers: {
                            'Content-Type': 'application/xml'
                        },
                        data: entity
                    }
                    $http(req).then(onSuccess, onError);
                }
            
            
        }
        
            }]);
})(angular);