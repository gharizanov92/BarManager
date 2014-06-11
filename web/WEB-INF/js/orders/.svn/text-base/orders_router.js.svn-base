'use strict';

app
    .config(['$routeProvider', '$httpProvider', '$translateProvider', 'USER_ROLES',
        function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/orders', {
                    templateUrl: 'views/orders.html',
                    controller: 'OrdersController',
                    resolve:{
                        resolvedOrders: ['Orders', function (Orders) {
                            return Orders.all();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        }]);
