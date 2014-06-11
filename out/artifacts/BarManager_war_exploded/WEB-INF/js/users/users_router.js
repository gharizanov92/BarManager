'use strict';

app
    .config(['$routeProvider', '$httpProvider', '$translateProvider', 'USER_ROLES',
        function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/users', {
                    templateUrl: 'views/users.html',
                    controller: 'UsersController',
                    resolve:{
                        resolvedUser: ['Users', function (Users) {
                            return Users.all();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.manager]
                    }
                })
        }]);
