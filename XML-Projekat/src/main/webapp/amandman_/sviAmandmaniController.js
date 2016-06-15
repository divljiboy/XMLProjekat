(function(angular)
{
	angular.module("xmlApp").controller("sviAmandmaniController", ['$scope','$state','$stateParams','amandmanService',function($scope,$state,$stateParams,amandmanService)
	{



		$scope.amandman = {};

		$scope.gridOptions = { enableRowSelection: true, enableRowHeaderSelection: false };

		$scope.gridOptions.columnDefs = [
			{name: 'id', visible : false },
			{name: 'koDodaje' }

		];

		$scope.gridOptions.multiSelect = false;
		$scope.gridOptions.noUnselect = true;
		$scope.gridOptions.onRegisterApi = function (gridApi) {
			$scope.gridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope, function (row) {

				$scope.amandman.koDodaje = row.entity.koDodaje;
				$scope.amandman.id = row.entity.id;





			});

		};


		amandmanService.getPredlozeneAmandmaneZaAkt($stateParams.aktId,
		function(res){
			$scope.gridOptions.data = res.data;
		},
		function(res)
		{

		});



		$scope.pogledajDetalje = function(){
			if($scope.gridApi.selection.getSelectedRows().length > 0){
				console.log($scope.amandman);
				$state.go("amandmanDetails",{"amandman":$scope.amandman});
			}
			else
			{
				alert("Niste selektovali amandman ! ");
			}
		}
	/*
		$scope.predlozi = function(){

			$state.go("noviAmandman");
		}

		*/
	}]);
})(angular);