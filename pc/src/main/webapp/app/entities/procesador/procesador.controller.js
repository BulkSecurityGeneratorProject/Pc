(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('ProcesadorController', ProcesadorController);

    ProcesadorController.$inject = ['Procesador'];

    function ProcesadorController(Procesador) {

        var vm = this;

        vm.procesadors = [];

        loadAll();

        function loadAll() {
            Procesador.query(function(result) {
                vm.procesadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
