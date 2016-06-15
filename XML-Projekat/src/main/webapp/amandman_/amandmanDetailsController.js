(function(angular){
    angular.module("xmlApp").controller("amandmanDetailsController",['$stateParams','$scope','$state','amandmanService','$sce',
        function($stateParams,$scope,$state,amandmanService,$sce)
        {

            (function(){
                amandmanService.get($stateParams.id,
                    function(res){
                        $scope.prikazAmandman = $sce.trustAsHtml(res.data);
                        console.log(res);
                        // console.log($scope.akt);
                    },
                    function(res){
                        console.log(res);
                    });

            })();
        }]);

})(angular);