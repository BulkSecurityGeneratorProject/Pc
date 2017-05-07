(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Ordenador', Ordenador);

    Ordenador.$inject = ['$resource'];

    function Ordenador ($resource) {
        var resourceUrl =  'api/ordenadors/:id';

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
