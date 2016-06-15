/**
 * Created by David on 6/15/2016.
 */

(function (angular) {

    angular.module("xmlApp").controller('brisanjeController',['$scope','$state','$uibModalInstance','aktService','amandmanService',function($scope,$state,$uibModalInstance,aktService,amandmanService){
        $scope.potvrdiBrisanje = function() {
            if($scope.entity === "akt")
            {
            aktService.delete($scope.akt.id, function (res) {

                $state.go('home');
            }, function (res) {

            });
            }
            else if($scope.entity === "amandman") {

                amandmanService.delete($scope.amandman.id, function (res) {
                     $state.go('home');
                }, function (res) {

                });

            }
        }

        $scope.odustani = function()
        {

            $uibModalInstance.dismiss('cancel');

        }

    }]);


        /*
        $scope.potvrdiPromenuStanja = function(){

            aktService.delete($scope.akt.id,function(res){
                console.log('Obrisan akt sa id - em'+$scope.akt.id);
                $state.go('home');
            },function(res)
            {

            });
        }

        $scope.odustani = function()
        {

            $uibModalInstance.dismiss('cancel');

        }
    */


})(angular);