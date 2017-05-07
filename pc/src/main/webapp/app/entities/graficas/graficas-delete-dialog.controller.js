(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('GraficasDeleteController',GraficasDeleteController);

    GraficasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Graficas'];

    function GraficasDeleteController($uibModalInstance, entity, Graficas) {
        var vm = this;

        vm.graficas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Graficas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
