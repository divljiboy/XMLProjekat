

(function(angular){
        
        angular.module("xmlApp").controller("noviAmandmanController",['$scope','$state','$stateParams', 'amandmanService',function($scope,$state,$stateParams, amandmanService)
                                                                     {

                                                                         $scope.akt = $stateParams.akt;
                                                                         



                                                                         $scope.predlogAmandmanaPotvrda = function(){

                                                                             amandmanService.create($scope.akt.id, $scope.amandman,
                                                                                                   function(res){
                                                                                                            alert("AMANDMAN PREDLOZEN");
                                                                             },
                                                                                                   function(res){
                                                                                                            alert("AMANDMAN PROPO");
                                                                             });

                                                                         }

                                                                     }]);
})(angular);

