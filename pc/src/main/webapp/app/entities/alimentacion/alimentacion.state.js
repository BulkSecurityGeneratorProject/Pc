(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alimentacion', {
            parent: 'entity',
            url: '/alimentacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.alimentacion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alimentacion/alimentacions.html',
                    controller: 'AlimentacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alimentacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('alimentacion-detail', {
            parent: 'alimentacion',
            url: '/alimentacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.alimentacion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alimentacion/alimentacion-detail.html',
                    controller: 'AlimentacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alimentacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Alimentacion', function($stateParams, Alimentacion) {
                    return Alimentacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alimentacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alimentacion-detail.edit', {
            parent: 'alimentacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alimentacion/alimentacion-dialog.html',
                    controller: 'AlimentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alimentacion', function(Alimentacion) {
                            return Alimentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alimentacion.new', {
            parent: 'alimentacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alimentacion/alimentacion-dialog.html',
                    controller: 'AlimentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                potencia: null,
                                flujoAire: null,
                                conectores: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alimentacion', null, { reload: 'alimentacion' });
                }, function() {
                    $state.go('alimentacion');
                });
            }]
        })
        .state('alimentacion.edit', {
            parent: 'alimentacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alimentacion/alimentacion-dialog.html',
                    controller: 'AlimentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alimentacion', function(Alimentacion) {
                            return Alimentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alimentacion', null, { reload: 'alimentacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alimentacion.delete', {
            parent: 'alimentacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alimentacion/alimentacion-delete-dialog.html',
                    controller: 'AlimentacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Alimentacion', function(Alimentacion) {
                            return Alimentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alimentacion', null, { reload: 'alimentacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
