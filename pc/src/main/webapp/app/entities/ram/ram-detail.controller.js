(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('RamDetailController', RamDetailController);

    RamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ram', 'Ordenador', 'Fabricante'];

    function RamDetailController($scope, $rootScope, $stateParams, previousState, entity, Ram, Ordenador, Fabricante) {
        var vm = this;

        vm.ram = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:ramUpdate', function(event, result) {
            vm.ram = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
