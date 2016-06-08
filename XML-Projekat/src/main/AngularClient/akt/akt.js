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
                            templateUrl: 'akt/sviAkti.html'
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