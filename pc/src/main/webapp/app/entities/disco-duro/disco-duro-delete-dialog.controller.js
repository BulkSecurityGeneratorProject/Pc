(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('DiscoDuroDeleteController',DiscoDuroDeleteController);

    DiscoDuroDeleteController.$inject = ['$uibModalInstance', 'entity', 'DiscoDuro'];

    function DiscoDuroDeleteController($uibModalInstance, entity, DiscoDuro) {
        var vm = this;

        vm.discoDuro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DiscoDuro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
