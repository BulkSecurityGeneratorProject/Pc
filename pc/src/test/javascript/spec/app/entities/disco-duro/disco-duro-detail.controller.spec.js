'use strict';

describe('Controller Tests', function() {

    describe('DiscoDuro Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiscoDuro, MockOrdenador, MockFabricante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiscoDuro = jasmine.createSpy('MockDiscoDuro');
            MockOrdenador = jasmine.createSpy('MockOrdenador');
            MockFabricante = jasmine.createSpy('MockFabricante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DiscoDuro': MockDiscoDuro,
                'Ordenador': MockOrdenador,
                'Fabricante': MockFabricante
            };
            createController = function() {
                $injector.get('$controller')("DiscoDuroDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcApp:discoDuroUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
