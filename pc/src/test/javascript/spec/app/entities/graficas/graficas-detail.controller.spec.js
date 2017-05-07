'use strict';

describe('Controller Tests', function() {

    describe('Graficas Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGraficas, MockOrdenador, MockFabricante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGraficas = jasmine.createSpy('MockGraficas');
            MockOrdenador = jasmine.createSpy('MockOrdenador');
            MockFabricante = jasmine.createSpy('MockFabricante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Graficas': MockGraficas,
                'Ordenador': MockOrdenador,
                'Fabricante': MockFabricante
            };
            createController = function() {
                $injector.get('$controller')("GraficasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcApp:graficasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
