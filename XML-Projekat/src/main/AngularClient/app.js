/**
 * 
 */

(function(angular){
	
	angular.module("xmlApp",['ui.router', 'ui.bootstrap']).config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');
        $stateProvider
          .state('home', {
              url: '/home',
              views: {
                  'navbar': {
                      templateUrl:'navbar/navbar.html'
                  },
                  'sidebar': {
                      templateUrl: 'sidebar/sidebar.html'
                  }
              }
          })
    }]);

})(angular);