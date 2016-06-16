(function(angular)
	{

		angular.module('xmlApp').config(['$stateProvider', function ($stateProvider) 
		{
            $stateProvider
            .state('sviAmandmani',
                {	
                    url: '/amandmani/aktId={id}',
                    views: {
                        'content': {
                            templateUrl: 'amandman_/sviAmandmani.html',
                            controller : "sviAmandmaniController"
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
                }).state('noviAmandman',
            {
                url: '/noviamandman/aktId={id}',

                views: {
                    'content': {
                        templateUrl: 'amandman_/NovAmandman.html',
                        controller : 'noviAmandmanController'

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
                
            }).state('amandmanDetails',
                {

                    url: '/amandmanDetails/{id}',
                    views: {
                        'content': {
                            templateUrl: 'amandman_/amandmanDetails.html',
                            controller: 'amandmanDetailsController'
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