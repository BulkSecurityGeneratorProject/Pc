(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OpticoDetailController', OpticoDetailController);

    OpticoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Optico', 'Ordenador', 'Fabricante'];

    function OpticoDetailController($scope, $rootScope, $stateParams, previousState, entity, Optico, Ordenador, Fabricante) {
        var vm = this;

        vm.optico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:opticoUpdate', function(event, result) {
            vm.optico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
