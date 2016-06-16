(function(angular){
    angular.module("xmlApp").controller("aktDetailsController",['$stateParams','$scope','$state','aktService','$sce','$anchorScroll','$timeout',
        function($stateParams,$scope,$state,aktService,$sce,$anchorScroll,$timeout)
        {

            (function(){
                aktService.get($stateParams.id,
                    function(res){
                        $scope.prikazAkt = $sce.trustAsHtml(res.data);
                        $timeout(function() {
                            $anchorScroll();
                        });
                    },
                    function(res){
                        console.log(res);
                    });

            })();
        }]);

})(angular);