(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('RamDeleteController',RamDeleteController);

    RamDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ram'];

    function RamDeleteController($uibModalInstance, entity, Ram) {
        var vm = this;

        vm.ram = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ram.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
