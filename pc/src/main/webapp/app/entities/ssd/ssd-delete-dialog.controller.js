(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('SsdDeleteController',SsdDeleteController);

    SsdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ssd'];

    function SsdDeleteController($uibModalInstance, entity, Ssd) {
        var vm = this;

        vm.ssd = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ssd.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
