/**
 * Created by David on 6/12/2016.
 */

(function(angular){

    angular.module("xmlApp").controller("glasanjeController",['$q','$scope','$state','amandmanService','aktService',function($q, $scope, $state,amandmanService, aktService){


        $scope.akatiIStanja = {};


        $scope.akati = {};


        aktService.getUProceduri(function(res){

                $scope.akati = res.data;

            //    $scope.ubaciAmandmane();



            angular.forEach($scope.akati, function(akt){
                $scope.amandmaniIStanja = {};
           //     console.log(akt.id);
                amandmanService.getPredlozeneAmandmaneZaAkt(akt.id, function (res) {
                        $scope.amandmani = [];

                        $scope.amandmani = res.data;
                    //    console.log($scope.amandmani.length);
                        var temp = 0;
                        angular.forEach($scope.amandmani, function(amandman){
                           // console.log(amandman);
                            console.log(temp);
                           // console.log(amandman.id);
                            $scope.amandmaniIStanja[temp] = {
                                'amandman': amandman,
                                'flag': false
                            } ;
                            console.log(amandman);
                            console.log($scope.amandmaniIStanja[temp]);
                            temp = temp + 1;


                        });

                            $scope.akatiIStanja[akt.id] = {

                            'akt': akt,
                            'amandmani': $scope.amandmaniIStanja,
                            'flag': false

                        }


                  },
                    function (res) {


                    });



            }
            );
        },function(res){

            });

 }]);
})(angular);