(function(angular){

    angular.module("xmlApp")
        .service('aktService', ['$http', function($http){
            return {

                //all
                getAll: function (onSuccess,onError) {
                    var req = {
                        method: 'GET',
                        url: 'akt',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    $http(req).then(onSuccess, onError);
                },
                //all
                getUsvojeni: function (onSuccess,onError) {
                    var req = {
                        method: 'GET',
                        url: '/usvojeniAkati',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    $http(req).then(onSuccess, onError);
                },
                //all
                getUProceduri : function (onSuccess,onError) {
                    var req = {
                        method: 'GET',
                        url: '/predlozeniAkati',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    $http(req).then(onSuccess, onError);
                },

                //byId
                get: function(id,onSuccess,onError){
                    var req = {
                        method: 'GET',
                        url: 'akt/'+id,
                        headers: {
                            'Content-Type': 'text/html'
                        },
                        transformResponse: [function (data) {
                            return data;
                        }]
                    };
                    $http(req).then(onSuccess, onError);
                },

                create: function(entity,onSuccess,onError){
                    var req = {
                        method: 'POST',
                        url: 'akt',
                        headers: {
                            'Content-Type': 'application/xml'
                        },
                        data: entity
                    }
                    $http(req).then(onSuccess, onError);
                },

                delete: function (id, onSuccess,onError) {
                    var req = {
                        method: 'DELETE',
                        url: 'akt/brisi/'+ id,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }
                    $http(req).then(onSuccess, onError);

                },
                filterProcedura: function (id, onSuccess,onError) {
                    var req = {
                        method: 'POST',
                        url: 'akt/brisi/'+ id,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }
                    $http(req).then(onSuccess, onError);

                },
                filterUsvojen: function (object, onSuccess,onError) {
                    var req = {
                        method: 'POST',
                        url: 'akt/usvojeni/pretraga',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: object
                    } ;

                    $http(req).then(onSuccess, onError);

                }

            }
        }]);

})(angular);