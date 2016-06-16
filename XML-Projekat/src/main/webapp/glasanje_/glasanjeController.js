/**
 * Created by David on 6/12/2016.
 */

(function(angular){

    angular.module("xmlApp").controller("glasanjeController",['$q','$scope','$state','amandmanService','aktService','glasanjeService',function($q, $scope, $state,amandmanService, aktService,glasanjeService){


        $scope.akatiIStanja = {};


        $scope.akati = {};

        var ucitajAmandmane = function(i){


            amandmanService.getPredlozeneAmandmaneZaAkt($scope.akati[i].id , function (res) {
                $scope.amandmani = [];
                $scope.amandmaniIStanja = [];
                $scope.amandmani = res.data;
                console.log($scope.akati[i].id);
                for(var j = 0; j < $scope.amandmani.length; j++){
                    console.log("-"+$scope.amandmani[j].id);

                    $scope.amandmaniIStanja[j] = {
                        "amandman" : $scope.amandmani[j],
                        "flag" : false
                    }
                }

                $scope.akatiIStanja[$scope.akati[i].id] = {
                    "akt" : $scope.akati[i],
                    "amandmani" : $scope.amandmaniIStanja,
                    "flag":false,
                    "za": '',
                    "protiv":'',
                    "uzdrzan":''

                }

            },
                function(res){

                });

        }

        aktService.getUProceduri(function(res){

                $scope.akati = res.data;

            //    $scope.ubaciAmandmane();


            for(var i = 0; i < $scope.akati.length; i++){
                 ucitajAmandmane(i);
            }

        },function(res){

            });


        $scope.rezultatGlasanja = function(){
            console.log("------->");
            var amandmani = [];
            var akati = [];

            angular.forEach($scope.akatiIStanja, function(akt){
                if(akt.flag == true){
                    akati.push(akt.akt.id);
                    angular.forEach(akt.amandmani,function(amandman){
                        if(amandman.flag == true){
                            amandmani.push(amandman.amandman.id);
                        }
                    });


                }
            });




            var glasanjeDto = {
                'actsIds' : akati,
                'amendmentsIds': amandmani
            }

            console.log(glasanjeDto);

            glasanjeService.glasaj(glasanjeDto,function(res){
                alert("mandzukic");
            },
            function(res){
                alert("propo");
            })
        }

        $scope.aktoviGlasanje = function()
        {
            for(var i = 0 ; i < $scope.akatiIStanja.length; i++){
                if( $scope.akatiIStanja[i].za > ($scope.akatiIStanja[i].protiv + $scope.akatiIStanja[i].uzdrzan))
                {
                    $scope.akatiIStanja[i].flag = true;
                }
                else{
                    $scope.akatiIStanja[i].flag = false;
                }
            }
        }

 }]);


})(angular);