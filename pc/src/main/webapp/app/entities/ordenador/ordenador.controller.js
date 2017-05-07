(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OrdenadorController', OrdenadorController);

    OrdenadorController.$inject = ['Ordenador'];

    function OrdenadorController(Ordenador) {

        var vm = this;

        vm.ordenadors = [];

        loadAll();

        function loadAll() {
            Ordenador.query(function(result) {
                vm.ordenadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
