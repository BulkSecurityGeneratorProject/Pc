(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('PlacaDialogController', PlacaDialogController);

    PlacaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Placa', 'Ordenador', 'Fabricante'];

    function PlacaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Placa, Ordenador, Fabricante) {
        var vm = this;

        vm.placa = entity;
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
            if (vm.placa.id !== null) {
                Placa.update(vm.placa, onSaveSuccess, onSaveError);
            } else {
                Placa.save(vm.placa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:placaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
