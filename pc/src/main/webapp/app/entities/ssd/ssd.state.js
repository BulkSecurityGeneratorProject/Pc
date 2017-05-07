(function() {
    'use strict';

    angular
        .module('pcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ssd', {
            parent: 'entity',
            url: '/ssd',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ssd.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ssd/ssds.html',
                    controller: 'SsdController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ssd');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ssd-detail', {
            parent: 'ssd',
            url: '/ssd/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pcApp.ssd.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ssd/ssd-detail.html',
                    controller: 'SsdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ssd');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ssd', function($stateParams, Ssd) {
                    return Ssd.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ssd',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ssd-detail.edit', {
            parent: 'ssd-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ssd/ssd-dialog.html',
                    controller: 'SsdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ssd', function(Ssd) {
                            return Ssd.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ssd.new', {
            parent: 'ssd',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ssd/ssd-dialog.html',
                    controller: 'SsdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                capacidad: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ssd', null, { reload: 'ssd' });
                }, function() {
                    $state.go('ssd');
                });
            }]
        })
        .state('ssd.edit', {
            parent: 'ssd',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ssd/ssd-dialog.html',
                    controller: 'SsdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ssd', function(Ssd) {
                            return Ssd.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ssd', null, { reload: 'ssd' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ssd.delete', {
            parent: 'ssd',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ssd/ssd-delete-dialog.html',
                    controller: 'SsdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ssd', function(Ssd) {
                            return Ssd.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ssd', null, { reload: 'ssd' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
