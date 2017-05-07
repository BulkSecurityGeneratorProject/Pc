(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('AlimentacionDetailController', AlimentacionDetailController);

    AlimentacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alimentacion', 'Ordenador', 'Fabricante'];

    function AlimentacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Alimentacion, Ordenador, Fabricante) {
        var vm = this;

        vm.alimentacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:alimentacionUpdate', function(event, result) {
            vm.alimentacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
