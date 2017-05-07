(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OrdenadorDeleteController',OrdenadorDeleteController);

    OrdenadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ordenador'];

    function OrdenadorDeleteController($uibModalInstance, entity, Ordenador) {
        var vm = this;

        vm.ordenador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ordenador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
