/**
 * Created by David on 6/17/2016.
 */

(function (angular) {

    angular.module("xmlApp").controller("pretragaMPController",['$scope','$state','aktService','$stateParams',function($scope,$state,aktService,$stateParams){

        $scope.potvrdi = function(){
            $scope.url = "1="+$scope.predlagac+"&2="+$scope.datum+"&3="+$scope.usvojio+"&4="+$scope.za+"&5="+$scope.protiv+"&6="+$scope.suzdrzano;


            $state.go("prikazifiltrirane",{url:$scope.url});

        }

        $scope.odustani = function(){
            $state.go("home");
        }
    }]);
})(angular);