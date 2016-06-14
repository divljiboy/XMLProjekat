(function(angular){
    angular.module("xmlApp").controller("amandmanDetailsController",['$stateParams','$scope','$state','aktService','$sce',
        function($stateParams,$scope,$state,amandmanService,$sce)
        {

            (function(){
                amandmanService.get($stateParams.amandman.id,
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

})(angular);/**
 * Created by Home on 6/14/2016.
 */
