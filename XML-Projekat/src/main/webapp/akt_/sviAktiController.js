(function(angular)
{
	angular.module("xmlApp").controller("sviAktiController", ['$scope','$state',function($scope, $state){
		$scope.message = "Akt welcome!"

		$scope.predlozi = function(){
			console.log("xxxx");
			$state.go("noviAkt");
		}
	}]);
})(angular);