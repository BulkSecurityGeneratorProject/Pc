(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OrdenadorDialogController', OrdenadorDialogController);

    OrdenadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ordenador', 'Procesador', 'DiscoDuro', 'Ram', 'Ssd', 'Optico', 'Graficas', 'Alimentacion', 'Placa'];

    function OrdenadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ordenador, Procesador, DiscoDuro, Ram, Ssd, Optico, Graficas, Alimentacion, Placa) {
        var vm = this;

        vm.ordenador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.procesadors = Procesador.query();
        vm.discoduros = DiscoDuro.query();
        vm.rams = Ram.query();
        vm.ssds = Ssd.query();
        vm.opticos = Optico.query();
        vm.graficas = Graficas.query();
        vm.alimentacions = Alimentacion.query();
        vm.placas = Placa.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ordenador.id !== null) {
                Ordenador.update(vm.ordenador, onSaveSuccess, onSaveError);
            } else {
                Ordenador.save(vm.ordenador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:ordenadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
