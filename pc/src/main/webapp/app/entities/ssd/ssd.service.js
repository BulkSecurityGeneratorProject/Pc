(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Ssd', Ssd);

    Ssd.$inject = ['$resource'];

    function Ssd ($resource) {
        var resourceUrl =  'api/ssds/:id';

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
