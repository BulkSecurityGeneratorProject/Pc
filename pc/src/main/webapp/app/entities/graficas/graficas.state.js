(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('graficas', {
            parent: 'entity',
            url: '/graficas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.graficas.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/graficas/graficas.html',
                    controller: 'GraficasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('graficas');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('graficas-detail', {
            parent: 'graficas',
            url: '/graficas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.graficas.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/graficas/graficas-detail.html',
                    controller: 'GraficasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('graficas');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Graficas', function($stateParams, Graficas) {
                    return Graficas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'graficas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('graficas-detail.edit', {
            parent: 'graficas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/graficas/graficas-dialog.html',
                    controller: 'GraficasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Graficas', function(Graficas) {
                            return Graficas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('graficas.new', {
            parent: 'graficas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/graficas/graficas-dialog.html',
                    controller: 'GraficasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                bus: null,
                                salidas: null,
                                refrigeracion: null,
                                compatibilidad: null,
                                chipset: null,
                                valocidad: null,
                                memoria: null,
                                ramdac: null,
                                streamProcesor: null,
                                shaderClock: null,
                                pixelRate: null,
                                shaderModel: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('graficas', null, { reload: 'graficas' });
                }, function() {
                    $state.go('graficas');
                });
            }]
        })
        .state('graficas.edit', {
            parent: 'graficas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/graficas/graficas-dialog.html',
                    controller: 'GraficasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Graficas', function(Graficas) {
                            return Graficas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('graficas', null, { reload: 'graficas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('graficas.delete', {
            parent: 'graficas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/graficas/graficas-delete-dialog.html',
                    controller: 'GraficasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Graficas', function(Graficas) {
                            return Graficas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('graficas', null, { reload: 'graficas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
