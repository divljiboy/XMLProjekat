/**
 * Created by David on 6/9/2016.
 */

(function(angular) {
    angular.module("xmlApp").controller("signInController",['$scope','$state','$rootScope', 'authService',function($scope, $state,$rootScope, authService) {

        $scope.signIn = function () {
            var credentials = {
                'username' : $scope.username,
                'password' : $scope.password
            };
            //console.log(credentials);
            authService.login(credentials,
                function (data, status, headers) {
                    if(status == 200) {
                        $rootScope.authenticated = true;
                        localStorage.setItem('user',JSON.stringify(data));
                        //console.log(headers('X-AUTH-TOKEN'));
                        localStorage.setItem("token", headers('X-AUTH-TOKEN'));

                        $state.go('home');
                    }
                    if(status == 204){
                        $scope.username = "";
                        $scope.password = "";
                        $scope.signInMessage = "Neuspesno logovanje";
                        return false;
                    }
                },
                function(response){
                    alert('Neuspesno logovanje');
                    $state.go('signIn');
                }

            )
        }

    }]);


})(angular);
