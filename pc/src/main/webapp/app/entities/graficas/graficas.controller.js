(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('GraficasController', GraficasController);

    GraficasController.$inject = ['Graficas'];

    function GraficasController(Graficas) {

        var vm = this;

        vm.graficas = [];

        loadAll();

        function loadAll() {
            Graficas.query(function(result) {
                vm.graficas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
