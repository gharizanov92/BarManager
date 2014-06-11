'use strict';

app.controller('UsersController', ['$scope', 'resolvedUser', 'Users', 'Register',
    function ($scope, resolvedUsers, Users) {

        $scope.role = "Bartender";
        $scope.users = resolvedUsers;

        $scope.create = function () {
            Users.save($scope.user,
                function () {

                    $scope.success = null;
                    $scope.users = Users.all();
                    $('#registerUserModal').modal('hide');
                    $scope.clear();

                    if ($scope.user.password != $scope.confirmPassword) {
                        $scope.doNotMatch = "ERROR";
                    } else {
                        $scope.registerAccount.langKey = $translate.use();
                        $scope.doNotMatch = null;

                        var user ={
                            "username":$scope.user.username,
                            "password":$scope.user.password,
                            "role":$scope.user.role
                        };

                        Users.save(user,
                            function (value, responseHeaders) {
                                $scope.error = null;
                                $scope.errorUserExists = null;
                                $scope.success = 'OK';
                            },
                            function (httpResponse) {
                                $scope.clear();
                                if (httpResponse.status === 304 &&
                                    httpResponse.data.error && httpResponse.data.error === "Not Modified") {
                                    $scope.error = null;
                                    $scope.errorUserExists = "ERROR";
                                } else {
                                    $scope.error = "ERROR";
                                    $scope.errorUserExists = null;
                                }
                            });
                    }
                });
        };

        $scope.update = function (id) {
            $scope.clear();
            $scope.user = Users.get({id: id});
            $('#registerUserModal').modal('show');
        };

        $scope.delete = function (id) {
            Users.delete({id: id},
                function () {
                    $scope.users = Users.all();
                });
        };

        $scope.clear = function () {
            $scope.user = {"username":null, "password":null, "role":null};
            //$scope.user = null;
        };
    }]);
