(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('ProcesadorDialogController', ProcesadorDialogController);

    ProcesadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Procesador', 'Ordenador', 'Fabricante'];

    function ProcesadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Procesador, Ordenador, Fabricante) {
        var vm = this;

        vm.procesador = entity;
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
            if (vm.procesador.id !== null) {
                Procesador.update(vm.procesador, onSaveSuccess, onSaveError);
            } else {
                Procesador.save(vm.procesador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:procesadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
