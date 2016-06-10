(function(angular){

    angular.module("xmlApp")
        .controller('dodajAktController',['$scope', '$state', '$log', 'aktService',
                            function ($scope, $state, $log, aktService)
        {
            $scope.createAct = function () {
                aktService.create($scope.noviAkt,
                    function (res) {
                        $state.go('sviAkti');
                    },
                    function (res) {
                        alert("Greska");
                    });
            };
            $scope.cancelAct = function () {
                $state.go('sviAkti');
            };

        }]);

})(angular);