(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Graficas', Graficas);

    Graficas.$inject = ['$resource'];

    function Graficas ($resource) {
        var resourceUrl =  'api/graficas/:id';

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
