(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Procesador', Procesador);

    Procesador.$inject = ['$resource'];

    function Procesador ($resource) {
        var resourceUrl =  'api/procesadors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
