(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ram', {
            parent: 'entity',
            url: '/ram',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ram.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ram/rams.html',
                    controller: 'RamController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ram');
                    $translatePartialLoader.addPart('zocalo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ram-detail', {
            parent: 'ram',
            url: '/ram/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ram.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ram/ram-detail.html',
                    controller: 'RamDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ram');
                    $translatePartialLoader.addPart('zocalo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ram', function($stateParams, Ram) {
                    return Ram.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ram',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ram-detail.edit', {
            parent: 'ram-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ram/ram-dialog.html',
                    controller: 'RamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ram', function(Ram) {
                            return Ram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ram.new', {
            parent: 'ram',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ram/ram-dialog.html',
                    controller: 'RamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                capacidad: null,
                                anchoDanda: null,
                                frecuencia: null,
                                tiempoAcceso: null,
                                latencia: null,
                                conectores: null,
                                zocalo: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ram', null, { reload: 'ram' });
                }, function() {
                    $state.go('ram');
                });
            }]
        })
        .state('ram.edit', {
            parent: 'ram',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ram/ram-dialog.html',
                    controller: 'RamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ram', function(Ram) {
                            return Ram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ram', null, { reload: 'ram' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ram.delete', {
            parent: 'ram',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ram/ram-delete-dialog.html',
                    controller: 'RamDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ram', function(Ram) {
                            return Ram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ram', null, { reload: 'ram' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
