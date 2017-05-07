'use strict';

describe('Controller Tests', function() {

    describe('Ordenador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrdenador, MockProcesador, MockDiscoDuro, MockRam, MockSsd, MockOptico, MockGraficas, MockAlimentacion, MockPlaca;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrdenador = jasmine.createSpy('MockOrdenador');
            MockProcesador = jasmine.createSpy('MockProcesador');
            MockDiscoDuro = jasmine.createSpy('MockDiscoDuro');
            MockRam = jasmine.createSpy('MockRam');
            MockSsd = jasmine.createSpy('MockSsd');
            MockOptico = jasmine.createSpy('MockOptico');
            MockGraficas = jasmine.createSpy('MockGraficas');
            MockAlimentacion = jasmine.createSpy('MockAlimentacion');
            MockPlaca = jasmine.createSpy('MockPlaca');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ordenador': MockOrdenador,
                'Procesador': MockProcesador,
                'DiscoDuro': MockDiscoDuro,
                'Ram': MockRam,
                'Ssd': MockSsd,
                'Optico': MockOptico,
                'Graficas': MockGraficas,
                'Alimentacion': MockAlimentacion,
                'Placa': MockPlaca
            };
            createController = function() {
                $injector.get('$controller')("OrdenadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcApp:ordenadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
