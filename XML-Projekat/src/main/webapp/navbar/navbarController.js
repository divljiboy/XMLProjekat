(function(angular){

    angular.module("xmlApp")
        .controller("navbarController",['$scope','$state','$rootScope',function($scope,$state,$rootScope) {

            $scope.signOut = function(){
                $rootScope.authenticated = false;
                $rootScope.ulogovanKorisnik = {};
                localStorage.removeItem('user');
                $state.go('home');
            }

        }]);

})(angular);