(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('OpticoController', OpticoController);

    OpticoController.$inject = ['Optico'];

    function OpticoController(Optico) {

        var vm = this;

        vm.opticos = [];

        loadAll();

        function loadAll() {
            Optico.query(function(result) {
                vm.opticos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
