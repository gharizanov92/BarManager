package bg.foosoft.project.controller.rest;

import bg.foosoft.project.controller.rest.body.OrderRequestBody;
import bg.foosoft.project.dao.OrdersDAO;
import bg.foosoft.project.dao.UserDAO;
import bg.foosoft.project.model.Order;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gharizanov on 11.6.2014 г..
 */
@Controller
@RequestMapping("/rest/orders")
public class OrdersController {

    @Autowired
    private UserInfo mUserInfo;

    @Autowired
    private OrdersDAO mOrdersDAO;

    @Autowired
    private UserDAO mUserDAO;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addItem(@RequestBody OrderRequestBody aOrder) {


        if (mOrdersDAO.addOrder(aOrder.getItems())) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<Order> getAllOrders(){
        List<Order> result = new LinkedList<Order>();
        for(Order order : mOrdersDAO.findAll()){
            result.add(order);
        }

        return result;
    }

    @RequestMapping(value = "/taken_order",
            method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Order getTakenOrder(){
        Order order = mOrdersDAO.getTakenOrder();
        return order;
    }

    @RequestMapping(value = "/cancel_order/{id}",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<String> cancelOrder(@PathVariable String id){
        mUserDAO.cancelOrder(mUserInfo.getId(), id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/take_order/{id}",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Order> takeOrder(@PathVariable String id){
        mOrdersDAO.setOrderTaken(id);
        return new ResponseEntity<Order>(mOrdersDAO.getTakenOrder(), HttpStatus.OK);
    }
}
