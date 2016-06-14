/**
 * Created by David on 6/12/2016.
 */

(function(angular){

    angular.module("xmlApp").controller("glasanjeController",['$scope','$state','amandmanService','aktService',function($scope, $state,amandmanService, aktService){


        $scope.akatiIStanja = {};


        $scope.akati = {};


        aktService.getUProceduri(function(res){

                $scope.akati = res.data;
                $scope.ubaciAmandmane();
                console.log($scope.akatiIStanja);



            },
            function(res){

            });



        $scope.ubaciAmandmane = function(){

            for(var i = 0 ; i< $scope.akati.length; i++){
                $scope.amandmani = {};
                $scope.amandmaniIStanja = [];

                amandmanService.getPredlozeneAmandmaneZaAkt($scope.akati[i].id, function (res) {


                        $scope.amandmani = res.data;

                        console.log($scope.amandmani);
                        $scope.popara($scope.amandmani);


                    },
                    function (res) {


                    });


                $scope.popara = function(amandmani) {
                    $scope.amandmani = amandmani;
                    for (var j = 0; j < $scope.amandmani.length; j++) {

                        $scope.amandmaniIStanja[j] = {
                            'amandman': $scope.amandmani[j],
                            'flag': false
                        }
                    }

                }
                    $scope.akatiIStanja[$scope.akati[i].id] = {

                        'akt': $scope.akati[i],
                        'amandmani': $scope.amandmaniIStanja,
                        'flag': false

                    }





            }

        }

        console.log($scope.akatiIStanja);




    }]);
})(angular);