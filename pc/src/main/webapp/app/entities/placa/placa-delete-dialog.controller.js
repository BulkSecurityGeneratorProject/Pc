(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('PlacaDeleteController',PlacaDeleteController);

    PlacaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Placa'];

    function PlacaDeleteController($uibModalInstance, entity, Placa) {
        var vm = this;

        vm.placa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Placa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
