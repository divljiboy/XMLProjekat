(function(angular){

    angular.module("xmlApp")
        .service('aktService', ['$http','$window',function($http,$window){
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
                get: function(id,nazivStanja,onSuccess,onError){
                    var req = {
                        method: 'GET',
                        url: 'akt/'+nazivStanja+'/'+id,
                        headers: {
                            'Content-Type': 'text/html'
                        },
                        transformResponse: [function (data) {
                            return data;
                        }]
                    };
                    $http(req).then(onSuccess, onError);
                },
                //pdf
                getPDF: function(id,nazivStanja){
                    $window.open('localhost:8080/akt/pdf/'+nazivStanja+'/'+id);
                    //var req = {
                    //    method: 'GET',
                    //    url: 'akt/pdf/'+nazivStanja+'/'+id,
                    //    headers: {
                    //        'Content-Type': 'application/pdf'
                    //    }
                    //};
                    //$http(req).then(function(res){
                    //        //var file = new Blob([res], {
                    //        //    type : 'application/pdf'
                    //        //});
                    //        ////trick to download store a file having its URL
                    //        //var fileURL = URL.createObjectURL(file);
                    //        //var a         = document.createElement('a');
                    //        //a.href        = ;
                    //        //a.target      = '_blank';
                    //        //a.download    = 'yourfilename.pdf';
                    //        //document.body.appendChild(a);
                    //        //a.click();
                    //    }
                    //    ,function(res){
                    //
                    //    });
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

                },
                searchByMetadata: function(dtoObject, onSuccess, onError){
                    var req = {
                            method : 'POST',
                            url: "akt/search/metadata/1",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            data: dtoObject
                        };
                    $http(req).then(onSuccess, onError);

                }



            }
        }]);

})(angular);