'use strict';

app.controller('OrdersController', ['$scope', 'resolvedOrders', 'Orders', 'Items', '$timeout', "$rootScope",
    function ($scope, resolvedOrders, Orders, Items, $timeout, $rootScope) {

        $scope.hasTakenOrder = true;
        $scope.orders = resolvedOrders;
        $scope.takenOrder = Orders.takenOrder(function(result){
            console.log(result);
            console.log("id" in result);
            if("id" in result){
                $scope.hasTakenOrder = true;
            } else {
                $scope.hasTakenOrder = false;
            }
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

        $scope.create = function () {
            var orders = [];

            Orders.save({"items":$scope.items},
                function () {
                    $scope.orderss = Orders.all();
                    $('#saveOrdersModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.takeOrder = function (id) {
            $scope.hasTakenOrder = true;
            $.post("/rest/orders/take_order/" + id, function(data){
                    $scope.takenOrder = data;
                }).fail(function(){
                    $scope.hasTakenOrder = false;
                });
        };

        $scope.cancelOrder = function(id){
            $scope.hasTakenOrder = false;
            $scope.takenOrder = null;
            $.post("/rest/orders/cancel_order/" + id);
        };

        $scope.completeOrder = function(id){
            $.post("/rest/orders/complete_order/" + id);
        };

        $scope.clear = function () {
            $scope.orders = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    }]);
