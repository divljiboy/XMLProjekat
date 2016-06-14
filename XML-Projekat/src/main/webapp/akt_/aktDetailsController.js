(function(angular){   
    angular.module("xmlApp").controller("aktDetailsController",['$stateParams','$scope','$state','aktService',function($stateParams,$state,$scope,aktService)
                                                               {

                                                                   (function(){

                                                                       aktService.get($stateParams.akt.id,
                                                                           function(res){

                                                                               
                                                                               console.log($scope.akt);
                                                                           },
                                                                           function(res){
                                                                               console.log("greska");
                                                                           });

                                                                   })();
                                                               }]);
    
})(angular);