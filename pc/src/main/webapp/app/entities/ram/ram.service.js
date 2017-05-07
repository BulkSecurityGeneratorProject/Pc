(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('Ram', Ram);

    Ram.$inject = ['$resource'];

    function Ram ($resource) {
        var resourceUrl =  'api/rams/:id';

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
