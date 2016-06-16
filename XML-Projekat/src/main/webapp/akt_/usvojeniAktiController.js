/**
 * Created by David on 6/13/2016.
 */

(function(angular)
{
    angular.module("xmlApp").controller("usvojeniAktiController", ['$scope','$state','aktService','$stateParams','$location',function($scope, $state,aktService,$stateParams,$location) {



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

    /*
        if($stateParams.filter === "filter")
        {
            console.log($scope.filterText);
            console.log($stateParams.filter);
            var object = {
                "criteria":$scope.filterText,
                "idSearch": 2
            };

            aktService.filterUsvojen(object,
                function(res){
                    $scope.gridOptions.data = res.data;
                    console.log(res.data);
                }),
                function(res){

                }
        }else
        {
            console.log("svi");
            aktService.getUsvojeni(function(res){

                    $scope.gridOptions.data = res.data;
                    console.log(res.data);
                },
                function(res)
                {

                    alert("Greska!");
                }
            );
        }
        */
        $scope.ucitajAkate = function() {
            aktService.getUsvojeni(function (res) {

                    $scope.gridOptions.data = res.data;
                    console.log(res.data);
                },
                function (res) {

                    alert("Greska!");
                }
            );
        };






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


        $scope.skiniPDF = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log("pdf: "+$scope.akt);

            }
            else
            {
                alert("Niste selektovali akt za pdf ! ");
            }
        }
        $scope.pogledajDetalje = function(){
            if($scope.gridApi.selection.getSelectedRows().length > 0){
                console.log($scope.akt);
                $state.go("aktDetails",{id:$scope.akt.id, nazivStanja:"usvojeni"});
            }
            else
            {
                alert("Niste selektovali akt ! ");
            }
        }
        $scope.filtriraj = function(){
            console.log("popara");
            var object = {
                "criteria":$scope.filterText,
                "idSearch": 2
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

        $scope.predlozi = function(){

            $state.go("filter");
        }
    }]);
})(angular);
