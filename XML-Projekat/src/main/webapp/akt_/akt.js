(function(angular)
	{

		angular.module('xmlApp').config(['$stateProvider', function ($stateProvider) 
		{
            $stateProvider
            .state('sviAkti',
                {	
                    url: '/sviAkti',                                                                                                                   
                    views: {
                        'content': {
                            templateUrl: 'akt_/sviAkti.html',
                            controller : "sviAktiController"
                        },
                        'navbar': {
                            templateUrl: 'navbar/navbar.html'
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html'
                        }

                    }
                })
            .state('noviAkt',
                {	
                    url: '/noviakt',                                                                                                                   
                    views: {
                        'content': {
                            templateUrl: 'akt_/NoviAkt.html',
                            controller: 'dodajAktController'
                        },
                        'navbar': {
                            templateUrl: 'navbar/navbar.html'
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html'
                        }
                    }
                });
        }]);


	})(angular);