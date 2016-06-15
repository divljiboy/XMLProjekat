/**
 * Created by David on 6/13/2016.
 */

(function(angular)
{
    angular.module("xmlApp").controller("usvojeniAktiController", ['$scope','$state','aktService',function($scope, $state,aktService){


        $scope.akt = {};

        $scope.gridOptions = { enableRowSelection: true, enableRowHeaderSelection: false };

        $scope.gridOptions.columnDefs = [
            {name: 'id', visible : false },
            { name: 'naziv' }

        ];

        $scope.gridOptions.multiSelect = false;
        $scope.gridOptions.noUnselect = true;
        $scope.gridOptions.onRegisterApi = function (gridApi) {
            $scope.gridApi = gridApi;
            gridApi.selection.on.rowSelectionChanged($scope, function (row) {

                $scope.akt.naziv = row.entity.naziv;
                $scope.akt.id = row.entity.id;




            });

        };



        aktService.getUsvojeni(function(res){

                $scope.gridOptions.data = res.data;
                console.log(res.data);
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


        $scope.pogledajDetalje = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
                $state.go("aktDetails",{"id":$scope.akt.id});
            }
            else
            {
                alert("Niste selektovali akt ! ");
            }
        }

        $scope.filtriraj = function(){

            var object = {
                "criteria":$scope.filter,
                "idSearch": 2
            };
            console.log(object);
            aktService.filterUsvojen(object,
            function(res){
                $scope.gridOptions.data = res.data;
                console.log(res.data);
            }),
            function(res){

            };
        }

        $scope.predlozi = function(){

            $state.go("filter");
        }
    }]);
})(angular);
