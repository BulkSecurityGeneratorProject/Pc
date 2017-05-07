(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('DiscoDuroDialogController', DiscoDuroDialogController);

    DiscoDuroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DiscoDuro', 'Ordenador', 'Fabricante'];

    function DiscoDuroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DiscoDuro, Ordenador, Fabricante) {
        var vm = this;

        vm.discoDuro = entity;
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
            if (vm.discoDuro.id !== null) {
                DiscoDuro.update(vm.discoDuro, onSaveSuccess, onSaveError);
            } else {
                DiscoDuro.save(vm.discoDuro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:discoDuroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
