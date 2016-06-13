(function(angular){
    
    angular.module("xmlApp").controller("changeStateController",['$uibModalInstance','$state','$scope','stateService',function($uibModalInstance, $state,$scope,stateService){
        /*
        $scope.promenaStanja = function(){
            $scope.animationsEnabled = true;
                    var modalInstance = $uibModal.open({
                        backdrop: false,
                        templateUrl: 'state_/changeState.html',
                        controller: 'changeStateController',
                        scope : $scope
                    });
            
            
        }

        */

        $scope.potvrdiPromenuStanja = function(){
            console.log("promena stanja");
            stateService.setState($scope.state, function(res){

                $state.go('glasanje');
            }, function(res){
                alert("Stanje nije promenjeno");
            });
        }

        $scope.odustani = function()
        {
            console.log("nikola");
            $uibModalInstance.dismiss('cancel');
        }

        

    }]);
})(angular);