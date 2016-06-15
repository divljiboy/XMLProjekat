/**
 * Created by David on 6/13/2016.
 */

(function(angular)
{
    angular.module("xmlApp").controller("aktiUProceduriController", ['$uibModal','$scope','$state','aktService',function($uibModal, $scope, $state,aktService){
        $scope.message = "Akt welcome!"
        $scope.entity = {};
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



        aktService.getUProceduri(function(res){

                $scope.gridOptions.data = res.data;
                console.log(res.data);
            },
            function(res)
            {

                alert("Greska!");
            }
        );





        $scope.otvoriAmandmaneUProceduri = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
                $state.go("sviAmandmani", {id : $scope.akt.id});
            }
            else
            {
                alert("Niste selektovali akt ! ");
            }

        }


        $scope.predlozi = function(){

            $state.go("noviAkt");

        }

        $scope.predloziAmandman = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
                $state.go("noviAmandman",{id:$scope.akt.id});
            }
            else
            {
                alert("Niste selektovali akt ! ");
            }
        }

        $scope.pogledajDetalje = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
                $state.go("aktDetails",{id:$scope.akt.id});
            }
            else
            {
                alert("Niste selektovali akt ! ");
            }
        }

        $scope.povuci = function () {



                if ($scope.gridApi.selection.getSelectedRows().length > 0) {
                    $scope.entity = "akt";
                    $scope.animationsEnabled = true;
                    var modalInstance = $uibModal.open({
                        backdrop: false,
                        templateUrl: 'brisanje_/brisanje.html',
                        controller: 'brisanjeController',
                        scope : $scope
                    });
                }
                else
                {
                    alert("Niste selektovali drzavu !");
                }




        }
    }]);
})(angular);
