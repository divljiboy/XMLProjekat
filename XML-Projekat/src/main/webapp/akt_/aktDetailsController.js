(function(angular){
    angular.module("xmlApp").controller("aktDetailsController",['$stateParams','$scope','$state','aktService','$sce',
        function($stateParams,$scope,$state,aktService,$sce)
        {

            (function(){
                aktService.get($stateParams.id,
                    function(res){
                        $scope.prikazAkt = $sce.trustAsHtml(res.data);;
                        console.log(res);
                        // console.log($scope.akt);
                    },
                    function(res){
                        console.log(res);
                    });

            })();
        }]);

})(angular);