(function(angular)
{
	angular.module("xmlApp").controller("sviAmandmaniController", ['$scope','$state',function($scope,$state)
	{
		$scope.message = "Amandman welcome!";

		$scope.predlozi = function(){

			$state.go("noviAmandman");
		}
	}]);
})(angular);