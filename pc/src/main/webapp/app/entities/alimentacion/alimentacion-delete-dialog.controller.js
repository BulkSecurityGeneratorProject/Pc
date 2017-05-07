(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('AlimentacionDeleteController',AlimentacionDeleteController);

    AlimentacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alimentacion'];

    function AlimentacionDeleteController($uibModalInstance, entity, Alimentacion) {
        var vm = this;

        vm.alimentacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alimentacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
