(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Placa', Placa);

    Placa.$inject = ['$resource'];

    function Placa ($resource) {
        var resourceUrl =  'api/placas/:id';

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
