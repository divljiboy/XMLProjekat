/**
 * 
 */

(function(angular){
	
	angular.module("xmlApp").config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    
        $stateProvider
          .state('login', {
              url: '/login',
              views: {
                  "content":{
                      templateUrl:'login/login.html'
                  }
              }
          })
         .state('register', {
              url: '/register',
              views: {
                  "content":{
                      templateUrl:'login/register.html'
                  }
              }
          })
         
    }]);

})(angular);