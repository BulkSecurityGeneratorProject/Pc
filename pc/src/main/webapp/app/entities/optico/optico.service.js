(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Optico', Optico);

    Optico.$inject = ['$resource'];

    function Optico ($resource) {
        var resourceUrl =  'api/opticos/:id';

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
