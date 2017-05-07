(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('DiscoDuroController', DiscoDuroController);

    DiscoDuroController.$inject = ['DiscoDuro'];

    function DiscoDuroController(DiscoDuro) {

        var vm = this;

        vm.discoDuros = [];

        loadAll();

        function loadAll() {
            DiscoDuro.query(function(result) {
                vm.discoDuros = result;
                vm.searchQuery = null;
            });
        }
    }
})();
