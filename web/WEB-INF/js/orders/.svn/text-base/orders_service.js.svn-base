'use strict';

app.factory('Orders', ['$resource',
    function ($resource) {
        return $resource('/rest/orders/:dest/:id', {}, {
            'all': { method: 'GET', params: {dest:"all"},isArray: true},
            'takenOrder': { method: 'GET', params: {dest:"taken_order"}},
            'get': { method: 'GET', params: {dest:"order"}}
        });
    }]);
