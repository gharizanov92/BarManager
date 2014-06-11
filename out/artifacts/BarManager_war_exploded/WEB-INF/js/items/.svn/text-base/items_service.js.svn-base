'use strict';

app.factory('Items', ['$resource',
    function ($resource) {
        return $resource('/rest/items/:id', {}, {
            'all': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
