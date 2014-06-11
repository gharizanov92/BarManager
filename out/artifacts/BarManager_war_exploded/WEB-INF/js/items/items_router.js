'use strict';

app
    .config(['$routeProvider', '$httpProvider', '$translateProvider', 'USER_ROLES',
        function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/items', {
                    templateUrl: 'views/items.html',
                    controller: 'ItemsController',
                    resolve:{
                        resolvedItems: ['Items', function (Items) {
                            return Items.all();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.manager]
                    }
                })
        }]);
