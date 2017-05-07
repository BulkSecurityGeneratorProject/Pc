(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OrdenadorDetailController', OrdenadorDetailController);

    OrdenadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ordenador', 'Procesador', 'DiscoDuro', 'Ram', 'Ssd', 'Optico', 'Graficas', 'Alimentacion', 'Placa'];

    function OrdenadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Ordenador, Procesador, DiscoDuro, Ram, Ssd, Optico, Graficas, Alimentacion, Placa) {
        var vm = this;

        vm.ordenador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:ordenadorUpdate', function(event, result) {
            vm.ordenador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
