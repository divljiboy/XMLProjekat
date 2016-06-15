(function(angular){
    
    angular.module("xmlApp").controller("changeStateController",['$uibModalInstance','$state','$scope','stateService','$rootScope',
                                                        function($uibModalInstance, $state,$scope,stateService,$rootScope){

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
            console.log("promena stanja");
            console.log($scope.state.state);
            stateService.setState($scope.state, function(res){
                if($scope.state.state === "Glasanje") {
                    $state.go('glasanje');
                }
                else {
                    $state.go('home');
                }
            }, function(res){
                alert("Stanje nije promenjeno");
            });
        }

        $scope.odustani = function()
        {
            console.log($rootScope.ulogovanKorisnik);
            $uibModalInstance.dismiss('cancel');

        }

        

    }]);
})(angular);