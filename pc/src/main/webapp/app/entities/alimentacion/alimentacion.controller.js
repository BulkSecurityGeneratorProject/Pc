(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('AlimentacionController', AlimentacionController);

    AlimentacionController.$inject = ['Alimentacion'];

    function AlimentacionController(Alimentacion) {

        var vm = this;

        vm.alimentacions = [];

        loadAll();

        function loadAll() {
            Alimentacion.query(function(result) {
                vm.alimentacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
