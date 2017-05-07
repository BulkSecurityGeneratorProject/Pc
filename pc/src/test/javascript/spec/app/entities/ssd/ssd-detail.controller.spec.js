'use strict';

describe('Controller Tests', function() {

    describe('Ssd Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSsd, MockOrdenador, MockFabricante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSsd = jasmine.createSpy('MockSsd');
            MockOrdenador = jasmine.createSpy('MockOrdenador');
            MockFabricante = jasmine.createSpy('MockFabricante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ssd': MockSsd,
                'Ordenador': MockOrdenador,
                'Fabricante': MockFabricante
            };
            createController = function() {
                $injector.get('$controller')("SsdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcApp:ssdUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
