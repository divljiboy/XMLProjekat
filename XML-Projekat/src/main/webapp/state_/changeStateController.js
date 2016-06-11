(function(angular){
    
    angular.module("xmlApp").controller("changeStateController",['$uibModalInstance','$state','$scope','stateService',function($uibModalInstance, $state,$scope,stateService){
        
        $scope.promenaStanja = function(){
            $scope.animationsEnabled = true;
                    var modalInstance = $uibModal.open({
                        backdrop: false,
                        templateUrl: 'state_/changeState.html',
                        controller: 'changeStateController',
                        scope : $scope
                    });
            
            
        }
        
        
        
        $scope.potvrdiPromenuStanja = function(){
            
            stateService.setState($scope.state, function(res){
                
                alert("Trenutno stanje" + res.data.state);
            }, function(res){
                alert("Stanje nije promenjeno");
            });
        }
        
        $scope.odustani = function()
        {
            $uibModalInstance.dismiss('cancel');
        }
        
        
    }]);
})(angular);