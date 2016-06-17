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
                            templateUrl: 'navbar/navbar.html',
                            controller: "navbarController"
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html',
                            controller: 'sidebarController'
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
                            templateUrl: 'navbar/navbar.html',
                            controller: "navbarController"
                        },
                        'sidebar': {
                            templateUrl: 'sidebar/sidebar.html',
                            controller: 'sidebarController'
                        }
                    }
                })
                .state('usvojeniAkti',
                {
                    url: '/usvojeniAkti',
                    views: {
                        'content': {
                            templateUrl: 'akt_/sviAkti.html',
                            controller : "usvojeniAktiController"
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
                })
                .state('aktiUProceduri',
                {
                    url: '/aktiuproceduri',
                    views: {
                        'content': {
                            templateUrl: 'akt_/sviAkti.html',
                            controller : "aktiUProceduriController"
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
                }).state('aktDetails',
                        {
                
                            url: '/aktDetails/{nazivStanja}/{id}',
                    views: {
                        'content': {
                            templateUrl: 'akt_/aktDetails.html',
                            controller: 'aktDetailsController'
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
            
            }).state("pretraga",
                {
                    url:"/pretragapometapodacima",
                    views: {
                        'content': {
                            templateUrl: 'akt_/formaPretraga.html',
                            controller: "pretragaMPController"
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
                }).state("prikazifiltrirane",{
                url:"/pretragapometapodacima{url}",
                views: {
                    'content': {
                        templateUrl: 'akt_/sviAkti.html',
                        controller: "usvojeniAktiController"
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