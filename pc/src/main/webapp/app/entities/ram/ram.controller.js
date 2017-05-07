(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('RamController', RamController);

    RamController.$inject = ['Ram'];

    function RamController(Ram) {

        var vm = this;

        vm.rams = [];

        loadAll();

        function loadAll() {
            Ram.query(function(result) {
                vm.rams = result;
                vm.searchQuery = null;
            });
        }
    }
})();
