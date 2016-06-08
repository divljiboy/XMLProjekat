/**
 * 
 */

(function(angular){
	
	angular.module("xmlApp",['ui.router', 'ui.bootstrap', 'ui.grid']).config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');
        $stateProvider
          .state('home', {
              url: '/home',
              views: {
                  'navbar': {
                      templateUrl: 'AngularClient/navbar/navbar.html'
                  },
                  'sidebar': {
                      templateUrl: 'AngularClient/sidebar/sidebar.html'
                  }
              }
          })
    }]);
})(angular);