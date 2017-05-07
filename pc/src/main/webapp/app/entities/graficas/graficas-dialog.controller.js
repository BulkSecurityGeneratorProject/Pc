(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('GraficasDialogController', GraficasDialogController);

    GraficasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Graficas', 'Ordenador', 'Fabricante'];

    function GraficasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Graficas, Ordenador, Fabricante) {
        var vm = this;

        vm.graficas = entity;
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
            if (vm.graficas.id !== null) {
                Graficas.update(vm.graficas, onSaveSuccess, onSaveError);
            } else {
                Graficas.save(vm.graficas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:graficasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
