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
            .state('noviakt',
                {	
                    url: '/noviakt',                                                                                                                   
                    views: {
                        'content': {
                            templateUrl: 'akt/NoviAkt.html'
                        },
                        'navbar': {
                            templateUrl: 'navbar/navbar.html'
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html'
                        }
                    }
                })
            .state('noviamandman',
                {	
                    url: '/noviamandman',                                                                                                                   
                    views: {
                        'content': {
                            templateUrl: 'akt/NovAmandman.html'
                        },
                        'navbar': {
                            templateUrl: 'navbar/navbar.html'
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html'
                        }
                    },
                    controller : "sviAktiController"

                    
                    

                });
        }]);


	})(angular);