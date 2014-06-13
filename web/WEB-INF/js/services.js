'use strict';

/* Services */

app.factory('Register', ['$resource',
    function ($resource) {
        return $resource('/register', {}, {
        });
    }]);

app.factory('Account', ['$resource',
    function ($resource) {
        return $resource('/account', {}, {
        });
    }]);

app.factory('Password', ['$resource',
    function ($resource) {
        return $resource('/account/change_password', {}, {
        });
    }]);

app.factory('Sessions', ['$resource',
    function ($resource) {
        return $resource('/account/sessions/:series', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }]);

app.factory('MetricsService', ['$resource',
    function ($resource) {
        return $resource('metrics/metrics', {}, {
            'get': { method: 'GET'}
        });
    }]);

app.factory('ThreadDumpService', ['$http',
    function ($http) {
        return {
            dump: function() {
                var promise = $http.get('dump').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    }]);

app.factory('HealthCheckService', ['$rootScope', '$http',
    function ($rootScope, $http) {
        return {
            check: function() {
                var promise = $http.get('health').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    }]);

app.factory('LogsService', ['$resource',
    function ($resource) {
        return $resource('app/rest/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel':  { method: 'PUT'}
        });
    }]);

app.factory('AuditsService', ['$http',
    function ($http) {
        return {
            findAll: function() {
                var promise = $http.get('app/rest/audits/all').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            findByDates: function(fromDate, toDate) {
                var promise = $http.get('app/rest/audits/byDates', {params: {fromDate: fromDate, toDate: toDate}}).then(function (response) {
                    return response.data;
                });
                return promise;
            }
        }
    }]);

app.factory('Session', [
    function () {
        this.create = function (username, userRole) {
            this.username = username;
            this.userRole = userRole;
        };
        this.invalidate = function () {
            this.username = null;
            this.userRole = null;
        };
        return this;
    }]);

app.constant('USER_ROLES', {
        all: '*',
        manager: 'ROLE_MANAGER',
        bartender: 'ROLE_BARTENDER',
        waiter: 'ROLE_WAITER'
    });

app.factory('AuthenticationSharedService', ['$rootScope', '$http', 'authService', 'Session', 'Account','USER_ROLES',
    function ($rootScope, $http, authService, Session, Account, USER_ROLES) {
        return {
            login: function (param) {
                var data ={"username":  param.username ,"password": param.password};
                var request = $.ajax({
                    type: "POST",
                    url: "login",
                    data: data,
                    dataType: "application/json;charset=UTF-8",
                    statusCode: {
                        200: function (data, status, headers, config) {
                            Account.get(function(data){
                                Session.create(data.username, data.role);
                                $rootScope.account = Session;
                                authService.loginConfirmed(data);
                            });
                        },
                        404: function(){
                            $rootScope.authenticationError = true;
                        },
                        500: function(){
                            $rootScope.authenticationError = true;
                        }
                    }
                }).error(function (data, status, headers, config) {
                    $rootScope.authenticated = false;
                });
            },
            valid: function (authorizedRoles) {

                $http.get('protected/transparent.gif', {
                    //ignoreAuthModule: 'ignoreAuthModule'
                }).success(function (data, status, headers, config) {
                    if (!Session.login) {
                        Account.get(function(data) {
                            Session.create(data.username, data.role);
                            $rootScope.account = Session;

                            if (!$rootScope.isAuthorized(authorizedRoles)) {
                                event.preventDefault();
                                // user is not allowed
                                $rootScope.$broadcast("event:auth-notAuthorized");
                            }

                            $rootScope.authenticated = true;
                        });
                    }
                    $rootScope.authenticated = !!Session.login;
                }).error(function (data, status, headers, config) {
                    $rootScope.authenticated = false;
                });
            },
            isAuthorized: function (authorizedRole) {

                if (authorizedRole == '*' || Session.userRole == USER_ROLES.manager) {
                    return true;
                }

                var isAuthorized = false;
                var authorized = (Session.userRole == authorizedRole);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }

                return isAuthorized;
            },
            logout: function () {
                $rootScope.authenticationError = false;
                $rootScope.authenticated = false;
                $rootScope.account = null;

                $http.get('/logout');
                Session.invalidate();
                authService.loginCancelled();
            }
        };
    }]);
