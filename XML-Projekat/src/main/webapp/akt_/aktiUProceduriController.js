/**
 * Created by David on 6/13/2016.
 */

(function(angular)
{
    angular.module("xmlApp").controller("aktiUProceduriController", ['$location','$uibModal','$scope','$state','aktService',function($location,$uibModal, $scope, $state,aktService){
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



        $scope.ucitajAkate = function() {
            aktService.getUProceduri(function (res) {

                    $scope.gridOptions.data = res.data;
                    console.log(res.data);
                },
                function (res) {

                    alert("Greska!");
                }
            );
        }




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
        $scope.pogledajPDF = function(){
           if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);*/
                $state.go("aktpdf",{id:$scope.akt.id});
            }
            else
            {
                alert("Niste selektovali akt za pdf ! ");
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


        $scope.filtriraj = function(){
            console.log("popara");
            var object = {
                "criteria":$scope.filterText,
                "idSearch": 1
            };
            aktService.filterUsvojen(object,
                function(res){
                    $scope.gridOptions.data = res.data;
                    $location.hash("search="+$scope.filterText);

                }),
                function(res){


                };
        }

        if ($location.hash().indexOf("search") != -1){

            $scope.filterText = $location.hash().split("=")[1];

            console.log($scope.filterText);


            $scope.filtriraj();

        }else
        {

            $scope.ucitajAkate();
        }
    }]);
})(angular);
