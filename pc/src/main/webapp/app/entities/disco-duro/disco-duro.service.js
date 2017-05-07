(function() {
    'use strict';
    angular
        .module('pcApp')
        .factory('DiscoDuro', DiscoDuro);

    DiscoDuro.$inject = ['$resource'];

    function DiscoDuro ($resource) {
        var resourceUrl =  'api/disco-duros/:id';

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
