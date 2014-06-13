'use strict';

app.controller('OrdersController', ['$scope', 'resolvedOrders', 'Orders', 'Items', '$timeout', "$rootScope",
    function ($scope, resolvedOrders, Orders, Items, $timeout, $rootScope) {

        $scope.outOfTime = 0;
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

        $scope.items = Items.all(function(){
            var decorate = function() {
                $(".add-quantity").click(function () {
                    var input = $(this).first().parent().parent().find("input");
                    var currentVal = parseInt(input.val());
                    if (isNaN(currentVal)) {
                        currentVal = 0;
                    }
                    input.val(currentVal + 1);
                });

                $(".remove-quantity").click(function () {
                    var input = $(this).first().parent().parent().find("input");
                    var currentVal = parseInt(input.val());
                    if (isNaN(currentVal)) {
                        currentVal = 0;
                    }
                    if (currentVal - 1 >= 0) {
                        input.val(currentVal - 1);
                    }
                });
            }
            setTimeout(decorate, 100);

        });

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

                    if(newOrders.length != $scope.orders.length){
                        $scope.orders = newOrders;
                        return;
                    }

                    var timeout = 0;
                    for(var i = 0; i < newOrders.length; i++){

                        if(newOrders[i].expired == true){
                            timeout++;
                        }

                        $scope.orders[i] = newOrders[i];
                        $scope.orders[i].time = formatTime(newOrders[i].remainingTime);
                    }
                    $scope.outOfTime = timeout;
                });
                updateOrders();
            }, 1000);
        };
        updateOrders();

        $scope.create = function () {
            var orders = [];

            Orders.save({"items":$scope.items},
                function (response) {
                    console.log(response);
                    $scope.orderss = Orders.all();
                    $scope.clear();
                });
        };

        $scope.cancelOrder = function(id){
            $scope.hasTakenOrder = false;
            $scope.takenOrder = null;
            $.post("/rest/orders/cancel_order/" + id);
        };

        $scope.takeOrder = function (id) {
            $scope.hasTakenOrder = true;
            $.post("/rest/orders/take_order/" + id, function(data){
                    $scope.takenOrder = data;
                }).fail(function(){
                    $scope.hasTakenOrder = false;
                    $scope.takenOrder = null;
                });
        };

        $scope.completeOrder = function(id){
            $.post("/rest/orders/complete_order/" + id, function(){
                $scope.hasTakenOrder = false;
                $scope.takenOrder = null;
            });
        };

        $scope.clear = function () {
            $scope.items = Items.all();
        };
    }]);
