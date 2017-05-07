(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('FabricanteDeleteController',FabricanteDeleteController);

    FabricanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fabricante'];

    function FabricanteDeleteController($uibModalInstance, entity, Fabricante) {
        var vm = this;

        vm.fabricante = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fabricante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
