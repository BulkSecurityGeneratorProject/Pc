(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Fabricante', Fabricante);

    Fabricante.$inject = ['$resource'];

    function Fabricante ($resource) {
        var resourceUrl =  'api/fabricantes/:id';

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
