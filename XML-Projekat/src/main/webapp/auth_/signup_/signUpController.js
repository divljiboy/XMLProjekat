/**
 * Created by David on 6/9/2016.
 */

(function(angular){

        angular.module("xmlApp").controller("signUpController",['$scope','$state','authService',function($scope,$state,authService)
        {
                    $scope.korisnik = {};



                    $scope.signUp = function () {
                        if($scope.ulogaKorisnika == true )
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