(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('SsdDetailController', SsdDetailController);

    SsdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ssd', 'Ordenador', 'Fabricante'];

    function SsdDetailController($scope, $rootScope, $stateParams, previousState, entity, Ssd, Ordenador, Fabricante) {
        var vm = this;

        vm.ssd = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcApp:ssdUpdate', function(event, result) {
            vm.ssd = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
