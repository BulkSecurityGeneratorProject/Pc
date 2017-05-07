(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('PlacaDetailController', PlacaDetailController);

    PlacaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Placa', 'Ordenador', 'Fabricante'];

    function PlacaDetailController($scope, $rootScope, $stateParams, previousState, entity, Placa, Ordenador, Fabricante) {
        var vm = this;

        vm.placa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:placaUpdate', function(event, result) {
            vm.placa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
