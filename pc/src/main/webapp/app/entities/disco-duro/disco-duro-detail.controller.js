(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('DiscoDuroDetailController', DiscoDuroDetailController);

    DiscoDuroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DiscoDuro', 'Ordenador', 'Fabricante'];

    function DiscoDuroDetailController($scope, $rootScope, $stateParams, previousState, entity, DiscoDuro, Ordenador, Fabricante) {
        var vm = this;

        vm.discoDuro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:discoDuroUpdate', function(event, result) {
            vm.discoDuro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
