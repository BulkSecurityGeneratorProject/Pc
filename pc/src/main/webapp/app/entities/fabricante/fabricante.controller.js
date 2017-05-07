(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('FabricanteController', FabricanteController);

    FabricanteController.$inject = ['Fabricante'];

    function FabricanteController(Fabricante) {

        var vm = this;

        vm.fabricantes = [];

        loadAll();

        function loadAll() {
            Fabricante.query(function(result) {
                vm.fabricantes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
