package bg.foosoft.project.controller.rest;

import bg.foosoft.project.controller.rest.body.OrderRequestBody;
import bg.foosoft.project.dao.OrdersDAO;
import bg.foosoft.project.model.Order;
import bg.foosoft.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gharizanov on 11.6.2014 г..
 */
@Controller
@RequestMapping("/rest/orders")
public class OrdersController {

    @Autowired
    private OrdersDAO mOrdersDAO;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addItem(@RequestBody OrderRequestBody aOrder) {


        if (mOrdersDAO.addOrder(aOrder.getTable(), aOrder.getItems())) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<Order> getAllUsers(){
        List<Order> result = new LinkedList<Order>();
        for(Order order : mOrdersDAO.findAll()){
            result.add(order);
        }

        return result;
    }
}
