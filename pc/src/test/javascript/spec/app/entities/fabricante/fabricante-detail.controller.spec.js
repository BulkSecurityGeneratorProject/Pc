'use strict';

describe('Controller Tests', function() {

    describe('Fabricante Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFabricante, MockDiscoDuro, MockRam, MockGraficas, MockAlimentacion, MockSsd, MockOptico, MockPlaca, MockProcesador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFabricante = jasmine.createSpy('MockFabricante');
            MockDiscoDuro = jasmine.createSpy('MockDiscoDuro');
            MockRam = jasmine.createSpy('MockRam');
            MockGraficas = jasmine.createSpy('MockGraficas');
            MockAlimentacion = jasmine.createSpy('MockAlimentacion');
            MockSsd = jasmine.createSpy('MockSsd');
            MockOptico = jasmine.createSpy('MockOptico');
            MockPlaca = jasmine.createSpy('MockPlaca');
            MockProcesador = jasmine.createSpy('MockProcesador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Fabricante': MockFabricante,
                'DiscoDuro': MockDiscoDuro,
                'Ram': MockRam,
                'Graficas': MockGraficas,
                'Alimentacion': MockAlimentacion,
                'Ssd': MockSsd,
                'Optico': MockOptico,
                'Placa': MockPlaca,
                'Procesador': MockProcesador
            };
            createController = function() {
                $injector.get('$controller')("FabricanteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcApp:fabricanteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
