(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('FabricanteDialogController', FabricanteDialogController);

    FabricanteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fabricante', 'DiscoDuro', 'Ram', 'Graficas', 'Alimentacion', 'Ssd', 'Optico', 'Placa', 'Procesador'];

    function FabricanteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fabricante, DiscoDuro, Ram, Graficas, Alimentacion, Ssd, Optico, Placa, Procesador) {
        var vm = this;

        vm.fabricante = entity;
        vm.clear = clear;
        vm.save = save;
        vm.discoduros = DiscoDuro.query();
        vm.rams = Ram.query();
        vm.graficas = Graficas.query();
        vm.alimentacions = Alimentacion.query();
        vm.ssds = Ssd.query();
        vm.opticos = Optico.query();
        vm.placas = Placa.query();
        vm.procesadors = Procesador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fabricante.id !== null) {
                Fabricante.update(vm.fabricante, onSaveSuccess, onSaveError);
            } else {
                Fabricante.save(vm.fabricante, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:fabricanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
