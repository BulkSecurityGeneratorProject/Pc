(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('SsdDialogController', SsdDialogController);

    SsdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ssd', 'Ordenador', 'Fabricante'];

    function SsdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ssd, Ordenador, Fabricante) {
        var vm = this;

        vm.ssd = entity;
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
            if (vm.ssd.id !== null) {
                Ssd.update(vm.ssd, onSaveSuccess, onSaveError);
            } else {
                Ssd.save(vm.ssd, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcApp:ssdUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
