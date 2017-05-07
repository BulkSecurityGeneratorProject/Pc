(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Alimentacion', Alimentacion);

    Alimentacion.$inject = ['$resource'];

    function Alimentacion ($resource) {
        var resourceUrl =  'api/alimentacions/:id';

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
