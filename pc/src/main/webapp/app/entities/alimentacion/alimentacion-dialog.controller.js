(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('AlimentacionDialogController', AlimentacionDialogController);

    AlimentacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alimentacion', 'Ordenador', 'Fabricante'];

    function AlimentacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Alimentacion, Ordenador, Fabricante) {
        var vm = this;

        vm.alimentacion = entity;
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
            if (vm.alimentacion.id !== null) {
                Alimentacion.update(vm.alimentacion, onSaveSuccess, onSaveError);
            } else {
                Alimentacion.save(vm.alimentacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:alimentacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
