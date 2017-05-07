(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OpticoDeleteController',OpticoDeleteController);

    OpticoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Optico'];

    function OpticoDeleteController($uibModalInstance, entity, Optico) {
        var vm = this;

        vm.optico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Optico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
