(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('ProcesadorDetailController', ProcesadorDetailController);

    ProcesadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Procesador', 'Ordenador', 'Fabricante'];

    function ProcesadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Procesador, Ordenador, Fabricante) {
        var vm = this;

        vm.procesador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:procesadorUpdate', function(event, result) {
            vm.procesador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
