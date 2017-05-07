(function() {
    'use strict';

    angular
        .module('pcApp')
        .controller('SsdController', SsdController);

    SsdController.$inject = ['Ssd'];

    function SsdController(Ssd) {

        var vm = this;

        vm.ssds = [];

        loadAll();

        function loadAll() {
            Ssd.query(function(result) {
                vm.ssds = result;
                vm.searchQuery = null;
            });
        }
    }
})();
