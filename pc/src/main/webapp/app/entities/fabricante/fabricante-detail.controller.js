(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('FabricanteDetailController', FabricanteDetailController);

    FabricanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fabricante', 'DiscoDuro', 'Ram', 'Graficas', 'Alimentacion', 'Ssd', 'Optico', 'Placa', 'Procesador'];

    function FabricanteDetailController($scope, $rootScope, $stateParams, previousState, entity, Fabricante, DiscoDuro, Ram, Graficas, Alimentacion, Ssd, Optico, Placa, Procesador) {
        var vm = this;

        vm.fabricante = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:fabricanteUpdate', function(event, result) {
            vm.fabricante = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
