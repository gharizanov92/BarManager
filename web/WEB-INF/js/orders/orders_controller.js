'use strict';

app.controller('OrdersController', ['$scope', 'resolvedOrders', 'Orders', 'Items', '$timeout', "$rootScope",
    function ($scope, resolvedOrders, Orders, Items, $timeout, $rootScope) {

        $scope.orders = resolvedOrders;
        $scope.takenOrder = Orders.takenOrder(function(result){
            console.log("taken order: ");
            console.log(result);
        });
        $scope.items = Items.all();

        var formatTime = function(time){
            var minutes = Math.floor(time / 60);
            var seconds = time % 60;
            if(seconds < 10){
                seconds = "0" + seconds;
            } else {
                seconds = "" + seconds;
            }
            return minutes + ":" + seconds;
        };

        var updateOrders = function() {
            $timeout(function() {
                Orders.all(function(newOrders){
                    for(var i = 0; i < newOrders.length; i++){
                        for(var j = 0; j < $scope.orders.length; j++){
                            if($scope.orders[j].id != newOrders[i].id){
                                $scope.orders = newOrders;
                            }
                        }
                    }

                    for(var i = 0; i < newOrders.length; i++){
                        $scope.orders[i].remainingTime = newOrders[i].remainingTime;
                        $scope.orders[i].time = formatTime(newOrders[i].remainingTime);
                    }
                });
                updateOrders();
            }, 1000);
        };
        updateOrders();

        $scope.hasOrder = function(){
            return false;
        };

        $scope.create = function () {
            console.log("test");
            var orders = [];

            Orders.save({"table":$scope.table, "items":$scope.items},
                function () {
                    $scope.orderss = Orders.all();
                    $('#saveOrdersModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.hasTakenOrder = function () {
            console.log($scope.takenOrder);
            return $scope.takenOrder != null;
        };

        $scope.takeOrder = function (id) {
            $.post("/rest/orders/take_order/" + id);
        };

        $scope.update = function (id) {
            $scope.orders = Orders.get({id: id});
            $('#saveOrdersModal').modal('show');
        };

        $scope.delete = function (id) {
            Orders.delete({id: id},
                function () {
                    $scope.orderss = Orders.all();
                });
        };

        $scope.clear = function () {
            $scope.orders = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    }]);
