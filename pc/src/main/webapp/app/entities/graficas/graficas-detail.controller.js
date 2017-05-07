(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('GraficasDetailController', GraficasDetailController);

    GraficasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Graficas', 'Ordenador', 'Fabricante'];

    function GraficasDetailController($scope, $rootScope, $stateParams, previousState, entity, Graficas, Ordenador, Fabricante) {
        var vm = this;

        vm.graficas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:graficasUpdate', function(event, result) {
            vm.graficas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
