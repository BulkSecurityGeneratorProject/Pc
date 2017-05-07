(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OpticoDialogController', OpticoDialogController);

    OpticoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Optico', 'Ordenador', 'Fabricante'];

    function OpticoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Optico, Ordenador, Fabricante) {
        var vm = this;

        vm.optico = entity;
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
            if (vm.optico.id !== null) {
                Optico.update(vm.optico, onSaveSuccess, onSaveError);
            } else {
                Optico.save(vm.optico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:opticoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
