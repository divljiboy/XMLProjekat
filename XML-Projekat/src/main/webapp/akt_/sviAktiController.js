(function(angular)
{
	angular.module("xmlApp").controller("sviAktiController", ['$scope','$state','aktService',function($scope, $state,aktService){
		$scope.message = "Akt welcome!"
        
         $scope.akt = {};
		
         $scope.gridOptions = { enableRowSelection: true, enableRowHeaderSelection: false };

            $scope.gridOptions.columnDefs = [
             
              { name: 'naziv' }

            ];

            $scope.gridOptions.multiSelect = false;
            $scope.gridOptions.noUnselect = true;
            $scope.gridOptions.onRegisterApi = function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                            
                         $scope.akt.naziv = row.entity.naziv;
                         
                    


                });

            };
                
        
            
            aktService.getAll(function(res){
                                
                                $scope.gridOptions.data = res.data;
                
            },
                              function(res)
                              {
                                    
                                    alert("Greska!");
            }
                );
        
        
        
        
        
        
        
        
        $scope.predlozi = function(){
            
			     $state.go("noviAkt");
            
		}
        
        $scope.predloziAmandman = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
			     $state.go("noviAmandman",{"akt":$scope.akt});
            }
            else 
            {
                alert("Niste selektovali akt ! ");
            }
		}
	}]);
})(angular);