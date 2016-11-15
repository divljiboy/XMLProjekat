/**
 * Created by David on 6/9/2016.
 */

(function(angular){

        angular.module("xmlApp").controller("signUpController",['$scope','$state','authService',function($scope,$state,authService)
        {
                    $scope.korisnik = {};

                    $scope.ulogaKorisnika_ = "predsednik";


                    $scope.signUp = function () {

                        if(!$scope.korisnik.ime){
                            $scope.alertMessage = "Ime mora biti uneto";
                        }else if(!$scope.korisnik.prezime){
                            $scope.alertMessage = "Prezime mora biti uneto";
                        }else if(!$scope.korisnik.username){
                            $scope.alertMessage = "Username polje mora biti uneto";
                        }else if(!$scope.korisnik.email){
                            $scope.alertMessage = "Email polje mora biti uneto";
                        }else if(!$scope.korisnik.password){
                            $scope.alertMessage = "Sifra mora biti uneto";
                        }else if(!$scope.repeatPassword){
                            $scope.alertMessage = "Ponovi sifru mora biti uneto";
                        }else if($scope.korisnik.password != $scope.repeatPassword)
                        {
                            $scope.alertMessage = "Sifre se ne poklapaju!!";
                        }
                        else{
                            $scope.alertMessage = null;
                        }
                        if($scope.alertMessage){
                            return;
                        }

                        console.log($scope.ulogaKorisnika_);
                        if($scope.ulogaKorisnika_ == "predsednik" )
                        {
                            $scope.korisnik.uloga = "Predsednik";
                        }else
                        {
                            $scope.korisnik.uloga = "Odbornik";
                        }

                        console.log($scope.korisnik);

                        authService.post($scope.korisnik,
                            function (resp) {
                                $state.go('signIn');
                            },
                            function (resp) {
                                console.log("Greska");
                            });
                    }
        }]);

})(angular);