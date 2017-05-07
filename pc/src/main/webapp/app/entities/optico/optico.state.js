(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('optico', {
            parent: 'entity',
            url: '/optico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.optico.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/optico/opticos.html',
                    controller: 'OpticoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('optico');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('optico-detail', {
            parent: 'optico',
            url: '/optico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.optico.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/optico/optico-detail.html',
                    controller: 'OpticoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('optico');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Optico', function($stateParams, Optico) {
                    return Optico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'optico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('optico-detail.edit', {
            parent: 'optico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/optico/optico-dialog.html',
                    controller: 'OpticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Optico', function(Optico) {
                            return Optico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('optico.new', {
            parent: 'optico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/optico/optico-dialog.html',
                    controller: 'OpticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                velocidadEscritura: null,
                                velocidadLectura: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('optico', null, { reload: 'optico' });
                }, function() {
                    $state.go('optico');
                });
            }]
        })
        .state('optico.edit', {
            parent: 'optico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/optico/optico-dialog.html',
                    controller: 'OpticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Optico', function(Optico) {
                            return Optico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('optico', null, { reload: 'optico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('optico.delete', {
            parent: 'optico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/optico/optico-delete-dialog.html',
                    controller: 'OpticoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Optico', function(Optico) {
                            return Optico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('optico', null, { reload: 'optico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
