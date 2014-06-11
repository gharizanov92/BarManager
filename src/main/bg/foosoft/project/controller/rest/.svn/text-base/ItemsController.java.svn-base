package bg.foosoft.project.controller.rest;

import bg.foosoft.project.dao.ItemsDAO;
import bg.foosoft.project.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gharizanov on 10.6.2014 г..
 */
@Controller
@RequestMapping("/rest/items")
public class ItemsController {

    @Autowired
    private ItemsDAO mItemsDAO;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addItem(@RequestBody MenuItem aNewItem) {

        if (mItemsDAO.addItem(aNewItem)) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<MenuItem> getAllItems() {
        List<MenuItem> result = mItemsDAO.findAll();

        return result;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<MenuItem> getParticularItem(@PathVariable String id) {
        MenuItem result = mItemsDAO.findItemByID(id);

        if (result == null) {
            return new ResponseEntity<MenuItem>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<MenuItem> removeItem(@PathVariable String id) {
        Boolean result = mItemsDAO.removeItem(id);

        if (result == false) {
            return new ResponseEntity<MenuItem>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
