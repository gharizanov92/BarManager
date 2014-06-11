'use strict';

app.controller('ItemsController', ['$scope', 'resolvedItems', 'Items',
    function ($scope, resolvedItems, Items) {

        $scope.role = "Bartender";
        $scope.items = resolvedItems;

        $scope.create = function () {
            Items.save($scope.item,
                function () {
                    $scope.success = null;
                    $scope.items = Items.all();
                    $('#addItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.clear();
            $scope.item = Items.get({id: id});
            $('#addItemModal').modal('show');
        };

        $scope.delete = function (id) {
            Items.delete({id: id},
                function () {
                    $scope.items = Items.all();
                });
        };

        $scope.clear = function () {
            $scope.item = null;
        };
    }]);
