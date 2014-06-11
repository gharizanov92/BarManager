'use strict';

app.factory('Orders', ['$resource',
    function ($resource) {
        return $resource('/rest/orders/:id', {}, {
            'all': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
