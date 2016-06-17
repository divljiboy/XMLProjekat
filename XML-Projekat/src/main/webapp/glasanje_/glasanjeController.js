/**
 * Created by David on 6/12/2016.
 */

(function(angular){

    angular.module("xmlApp").controller("glasanjeController",['$q','$scope','$state','amandmanService','aktService','glasanjeService',function($q, $scope, $state,amandmanService, aktService,glasanjeService){

        //nisu  disejblovana text polja
        $scope.disabled = false;
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
                    "za": 0,
                    "protiv":0,
                    "uzdrzan": 0

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
            var za = [];
            var protiv = [];
            var suzdrzano = [];

            angular.forEach($scope.akatiIStanja, function(akt){
                if(akt.flag == true){
                    akati.push(akt.akt.id);
                    za.push(akt.za);
                    protiv.push(akt.protiv);
                    suzdrzano.push(akt.suzdrzano);
                    angular.forEach(akt.amandmani,function(amandman){
                        if(amandman.flag == true){
                            amandmani.push(amandman.amandman.id);
                        }
                    });


                }
            });

            /*
             public Long idAct;
             public Integer za;
             public Integer protiv;
             public Integer suzdrzano;




             */
            var aktDTO = {
                "idAct": akati,
                "za":za,
                "protiv":protiv,
                "suzdrzano": suzdrzano
            }

            var objectDto = {
                'actsIds' : akati,
                'amendmentsIds': amandmani
            }

            console.log(glasanjeDto);

            glasanjeService.glasaj(glasanjeDto,function(res){
                alert("mandzukic");
            },
            function(res){
                alert("propo");
            });
        }

        $scope.aktoviGlasanje = function()
        {
            console.log("nikola");

            angular.forEach($scope.akatiIStanja, function(akt){
                console.log(akt.flag);
                console.log( akt.za);
                console.log( akt.protiv);

                if(akt.za > -1 && akt.protiv > -1 && akt.uzdrzan > -1){
                        $scope.disabled = true;
                        if( akt.za > akt.protiv)
                        {
                            akt.flag = true;

                        }
                        else if(akt.za == akt.protiv){
                            console.log("jednaki su");
                        }
                        else{
                            akt.flag = false;
                        }
                }
                else{
                    //ovo treba srediti
                    alert("Moraju se uneti svi pozitivni brojevi za akt sa nazivom");
                    return;

                }

            });
            /*
            console.log( $scope.akatiIStanja.length);
            for(var i = 0 ; i < $scope.akatiIStanja.length; i++){
                console.log($scope.akatiIStanja[i].za);
                console.log($scope.akatiIStanja[i].protiv);
                if( $scope.akatiIStanja[i].za > ($scope.akatiIStanja[i].protiv))
                {
                    $scope.akatiIStanja[i].flag = true;
                }
                else{
                    $scope.akatiIStanja[i].flag = false;
                }
            }
            */
        }

 }]);


})(angular);