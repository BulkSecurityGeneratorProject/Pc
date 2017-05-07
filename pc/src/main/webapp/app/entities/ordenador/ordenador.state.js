(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ordenador', {
            parent: 'entity',
            url: '/ordenador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ordenador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordenador/ordenadors.html',
                    controller: 'OrdenadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ordenador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ordenador-detail', {
            parent: 'ordenador',
            url: '/ordenador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ordenador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordenador/ordenador-detail.html',
                    controller: 'OrdenadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ordenador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ordenador', function($stateParams, Ordenador) {
                    return Ordenador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ordenador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ordenador-detail.edit', {
            parent: 'ordenador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordenador/ordenador-dialog.html',
                    controller: 'OrdenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ordenador', function(Ordenador) {
                            return Ordenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordenador.new', {
            parent: 'ordenador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordenador/ordenador-dialog.html',
                    controller: 'OrdenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                observaciones: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ordenador', null, { reload: 'ordenador' });
                }, function() {
                    $state.go('ordenador');
                });
            }]
        })
        .state('ordenador.edit', {
            parent: 'ordenador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordenador/ordenador-dialog.html',
                    controller: 'OrdenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ordenador', function(Ordenador) {
                            return Ordenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordenador', null, { reload: 'ordenador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordenador.delete', {
            parent: 'ordenador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordenador/ordenador-delete-dialog.html',
                    controller: 'OrdenadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ordenador', function(Ordenador) {
                            return Ordenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordenador', null, { reload: 'ordenador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
