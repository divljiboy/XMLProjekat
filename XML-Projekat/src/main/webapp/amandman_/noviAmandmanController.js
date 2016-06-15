

(function(angular){
        
        angular.module("xmlApp").controller("noviAmandmanController",['$scope','$state','$stateParams', 'amandmanService',function($scope,$state,$stateParams, amandmanService)
                                                                     {


                                                                         



                                                                         $scope.predlogAmandmanaPotvrda = function(){
                                                                             console.log($stateParams.id);
                                                                             amandmanService.create($stateParams.id, $scope.amandman,
                                                                                                   function(res){
                                                                                                            alert("AMANDMAN PREDLOZEN");
                                                                             },
                                                                                                   function(res){
                                                                                                            alert("AMANDMAN PROPO");
                                                                             });

                                                                         }

                                                                         $scope.odustani = function(){
                                                                             $state.go('home');
                                                                         }

                                                                     }]);
})(angular);

