(function(angular)
	{

		angular.module('xmlApp').config(['$stateProvider', function ($stateProvider) 
		{
            $stateProvider
            .state('sviAmandmani',
                {	
                    url: '/sviAmandmani',                                                                                                                   
                    views: {
                        'content': {
                            templateUrl: 'amandman_/sviAmandmani.html',
                            controller : "sviAmandmaniController"
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