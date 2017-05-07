(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('disco-duro', {
            parent: 'entity',
            url: '/disco-duro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.discoDuro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disco-duro/disco-duros.html',
                    controller: 'DiscoDuroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discoDuro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('disco-duro-detail', {
            parent: 'disco-duro',
            url: '/disco-duro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.discoDuro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disco-duro/disco-duro-detail.html',
                    controller: 'DiscoDuroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discoDuro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DiscoDuro', function($stateParams, DiscoDuro) {
                    return DiscoDuro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'disco-duro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('disco-duro-detail.edit', {
            parent: 'disco-duro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disco-duro/disco-duro-dialog.html',
                    controller: 'DiscoDuroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscoDuro', function(DiscoDuro) {
                            return DiscoDuro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disco-duro.new', {
            parent: 'disco-duro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disco-duro/disco-duro-dialog.html',
                    controller: 'DiscoDuroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                capacidad: null,
                                tamano: null,
                                tiempoAcceso: null,
                                tasaTransferencia: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disco-duro', null, { reload: 'disco-duro' });
                }, function() {
                    $state.go('disco-duro');
                });
            }]
        })
        .state('disco-duro.edit', {
            parent: 'disco-duro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disco-duro/disco-duro-dialog.html',
                    controller: 'DiscoDuroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscoDuro', function(DiscoDuro) {
                            return DiscoDuro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disco-duro', null, { reload: 'disco-duro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disco-duro.delete', {
            parent: 'disco-duro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disco-duro/disco-duro-delete-dialog.html',
                    controller: 'DiscoDuroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DiscoDuro', function(DiscoDuro) {
                            return DiscoDuro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disco-duro', null, { reload: 'disco-duro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
