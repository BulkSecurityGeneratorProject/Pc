(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('PlacaController', PlacaController);

    PlacaController.$inject = ['Placa'];

    function PlacaController(Placa) {

        var vm = this;

        vm.placas = [];

        loadAll();

        function loadAll() {
            Placa.query(function(result) {
                vm.placas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
