(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('ProcesadorDeleteController',ProcesadorDeleteController);

    ProcesadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procesador'];

    function ProcesadorDeleteController($uibModalInstance, entity, Procesador) {
        var vm = this;

        vm.procesador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procesador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
