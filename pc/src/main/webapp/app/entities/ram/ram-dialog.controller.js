(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('RamDialogController', RamDialogController);

    RamDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ram', 'Ordenador', 'Fabricante'];

    function RamDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ram, Ordenador, Fabricante) {
        var vm = this;

        vm.ram = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ordenadors = Ordenador.query();
        vm.fabricantes = Fabricante.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ram.id !== null) {
                Ram.update(vm.ram, onSaveSuccess, onSaveError);
            } else {
                Ram.save(vm.ram, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:ramUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
