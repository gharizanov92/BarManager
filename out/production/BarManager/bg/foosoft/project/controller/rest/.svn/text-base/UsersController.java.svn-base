package bg.foosoft.project.controller.rest;

import bg.foosoft.project.dao.UserDAO;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kayne on 8.6.2014 г..
 */
@Controller
@RequestMapping("/rest/users")
public class UsersController {

    @Autowired
    private UserDAO mUserDAO;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody User aNewUser){
        //aNewUser.addRole("ROLE_ADMIN");
        mUserDAO.registerUser(aNewUser);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<User> getAllUsers(){
        List<User> result = new LinkedList<User>();
        for(User user : mUserDAO.findAll()){
            if(!user.isManager()){
                result.add(user);
            }
        }
        
        return result;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    ResponseEntity<User> getParticularUser(@PathVariable String id){
        User result = mUserDAO.findUserByID(id);

        if(result == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        result.setPassword(null);

        return new ResponseEntity(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public @ResponseBody
    ResponseEntity<User> deleteParticularUser(@PathVariable String id){
        mUserDAO.removeUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    public UserDAO getUserDAO() {
        return mUserDAO;
    }

    public void setUserDAO(UserDAO aUserDAO) {
        mUserDAO = aUserDAO;
    }
}
