(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('placa', {
            parent: 'entity',
            url: '/placa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.placa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/placa/placas.html',
                    controller: 'PlacaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('placa');
                    $translatePartialLoader.addPart('tipo');
                    $translatePartialLoader.addPart('socket');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('placa-detail', {
            parent: 'placa',
            url: '/placa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.placa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/placa/placa-detail.html',
                    controller: 'PlacaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('placa');
                    $translatePartialLoader.addPart('tipo');
                    $translatePartialLoader.addPart('socket');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Placa', function($stateParams, Placa) {
                    return Placa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'placa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('placa-detail.edit', {
            parent: 'placa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/placa/placa-dialog.html',
                    controller: 'PlacaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Placa', function(Placa) {
                            return Placa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('placa.new', {
            parent: 'placa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/placa/placa-dialog.html',
                    controller: 'PlacaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                tipo: null,
                                procesadores: null,
                                memoria: null,
                                pci: null,
                                socket: null,
                                almacenamiento: null,
                                bios: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('placa', null, { reload: 'placa' });
                }, function() {
                    $state.go('placa');
                });
            }]
        })
        .state('placa.edit', {
            parent: 'placa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/placa/placa-dialog.html',
                    controller: 'PlacaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Placa', function(Placa) {
                            return Placa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('placa', null, { reload: 'placa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('placa.delete', {
            parent: 'placa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/placa/placa-delete-dialog.html',
                    controller: 'PlacaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Placa', function(Placa) {
                            return Placa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('placa', null, { reload: 'placa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
