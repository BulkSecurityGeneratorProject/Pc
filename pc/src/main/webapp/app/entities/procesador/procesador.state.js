(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('procesador', {
            parent: 'entity',
            url: '/procesador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.procesador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procesador/procesadors.html',
                    controller: 'ProcesadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('procesador');
                    $translatePartialLoader.addPart('socket');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('procesador-detail', {
            parent: 'procesador',
            url: '/procesador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.procesador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procesador/procesador-detail.html',
                    controller: 'ProcesadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('procesador');
                    $translatePartialLoader.addPart('socket');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Procesador', function($stateParams, Procesador) {
                    return Procesador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'procesador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('procesador-detail.edit', {
            parent: 'procesador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procesador/procesador-dialog.html',
                    controller: 'ProcesadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procesador', function(Procesador) {
                            return Procesador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procesador.new', {
            parent: 'procesador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procesador/procesador-dialog.html',
                    controller: 'ProcesadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombreProcesador: null,
                                memoria: null,
                                nucleos: null,
                                ghz: null,
                                velocidad: null,
                                socket: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('procesador', null, { reload: 'procesador' });
                }, function() {
                    $state.go('procesador');
                });
            }]
        })
        .state('procesador.edit', {
            parent: 'procesador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procesador/procesador-dialog.html',
                    controller: 'ProcesadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procesador', function(Procesador) {
                            return Procesador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procesador', null, { reload: 'procesador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procesador.delete', {
            parent: 'procesador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procesador/procesador-delete-dialog.html',
                    controller: 'ProcesadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Procesador', function(Procesador) {
                            return Procesador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procesador', null, { reload: 'procesador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
