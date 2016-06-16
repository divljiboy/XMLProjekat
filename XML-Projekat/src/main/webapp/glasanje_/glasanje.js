/**
 * Created by David on 6/12/2016.
 */


(function (angular) {

        angular.module("xmlApp").config(['$stateProvider',function($stateProvider){
            $stateProvider.state("glasanje",{

                url : "/glasanje",
                views : {
                    'content': {
                        templateUrl: 'glasanje_/glasanje.html',
                        controller : "glasanjeController"
                    },
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: "navbarController"
                    },
                    'sidebar': {
                        templateUrl: 'sidebar/sidebar.html',
                        controller: 'sidebarController'
                    }
                }

            });
        }]);
})(angular);
