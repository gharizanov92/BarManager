'use strict';

app.controller('OrdersController', ['$scope', 'resolvedOrders', 'Orders', 'Items', '$timeout',
    function ($scope, resolvedOrders, Orders, Items, $timeout) {

        $scope.orders = resolvedOrders;
        $scope.items = Items.all();

        var formatTime = function(time){
            var minutes = time / 60;
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
                        console.log(newOrders[i].id);
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
            console.log("test");
            var orders = [];
            $(".item").each(function(index){
                var numberOfOrders = $(this).find("input").val();
                for(var i = 0; i < numberOfOrders ; i++){
                    orders.push($(this).find("td").first().text().trim());
                }
            });
            console.log(orders);

            Orders.save({"table":$scope.table, "items":orders},
                function () {
                    $scope.orderss = Orders.all();
                    $('#saveOrdersModal').modal('hide');
                    $scope.clear();
                });
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
