(function(angular){
    
    angular.module("xmlApp").controller("sidebarController",['$uibModal','$state','$scope','stateService',function($uibModal, $state,$scope,stateService){
        
        $scope.promenaStanja = function(){
            $scope.animationsEnabled = true;
                    var modalInstance = $uibModal.open({
                        backdrop: false,
                        templateUrl: 'state_/changeState.html',
                        controller: 'changeStateController',
                        scope : $scope
                    });
            
            
        }
    }]);
})(angular);