'use strict';

app.factory('Users', ['$resource',
    function ($resource) {
        return $resource('/rest/users/:id', {}, {
            'all': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
