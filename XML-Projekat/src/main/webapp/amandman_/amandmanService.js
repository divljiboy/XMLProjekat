(function(angular){

    angular.module("xmlApp")
        .service('amandmanService', ['$http', function($http){
            return {
                /*
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

                //byId
                get: function(id,onSuccess,onError){
                    var req = {
                        method: 'GET',
                        url: 'akt/'+id,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    $http(req).then(onSuccess, onError);
                },
                */

                //prelozeniAmandmaniZaAkt
                getPredlozeneAmandmaneZaAkt : function(aktId, onSuccess,onError){
                    var  req = {
                        method: 'GET',
                        url: "amandman/"+aktId,
                        headers: {
                            'Content-Type': 'application/json'
                        }


                    }
                    $http(req).then(onSuccess,onError);
                },
                //byId
                get: function(id,onSuccess,onError){
                    var req = {
                        method: 'GET',
                        url: 'amandman/'+id,
                        headers: {
                            'Content-Type': 'text/html'
                        },
                        transformResponse: [function (data) {
                            return data;
                        }]
                    };
                    $http(req).then(onSuccess, onError);
                },
                create: function(aktId, entity,onSuccess,onError){
                    var req = {
                        method: 'POST',
                        url: 'amandman/'+aktId,
                        headers: {
                            'Content-Type': 'application/xml'
                        },
                        data: entity
                    }
                    $http(req).then(onSuccess, onError);
                }

            }
        }]);

})(angular);