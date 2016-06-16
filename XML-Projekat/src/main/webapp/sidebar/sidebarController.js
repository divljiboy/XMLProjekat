(function(angular){
    
    angular.module("xmlApp").controller("sidebarController",['$uibModal','$state','$scope','stateService','$rootScope',
                                                        function($uibModal, $state,$scope,stateService,$rootScope){
        
        $scope.promenaStanja = function(){

            $scope.animationsEnabled = true;
                    var modalInstance = $uibModal.open({
                        backdrop: false,
                        templateUrl: 'state_/changeState.html',
                        controller: 'changeStateController',
                        scope : $scope
                    });
            
            
        };

        (function(){
            var ulogovanKorisnik = JSON.parse(localStorage.getItem('user'));
            if(ulogovanKorisnik){
                $rootScope.ulogovanKorisnik = ulogovanKorisnik;
                $rootScope.authenticated = true;
                stateService.getState(function(res){
                    $scope.stateName = res.data.state;

                }, function(res){
                    alert("propo");
                });
            }else{
                $rootScope.ulogovanKorisnik = {};
                $rootScope.authenticated = false;
            }





        })();

    }]);
})(angular);