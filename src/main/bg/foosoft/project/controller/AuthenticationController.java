package bg.foosoft.project.controller;

import bg.foosoft.project.dao.UserDAO;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.PasswordHash;
import bg.foosoft.project.util.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayne on 25.5.2014 г..
 */
@Controller
@Scope("request")
@RequestMapping(value = "/")
public class AuthenticationController {

    @Autowired
    private UserInfo mUserInfo;

    @Autowired
    private UserDAO mDao;

    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<User> getAccountInfo() {
        User user = mUserInfo.getUserEntity();

        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(mUserInfo.getUserEntity(), HttpStatus.OK);
    }

    @RequestMapping(value = "/account/change_password",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<String> changePassword(@RequestBody String newPassword) {
        try{
            mDao.changePassword(mUserInfo.getId(), newPassword);
        } catch (Exception ex){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<User> authenticateUser(@ModelAttribute("user") User aUser){
        try{
            for(User user : mDao.findAll()){
                if(user.getUsername().equals(aUser.getUsername()) &&
                        PasswordHash.validatePassword(aUser.getPassword(), user.getPassword())){
                    mUserInfo.setId(user.getId());
                    mUserInfo.setUserEntity(user);
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                }
            }
        } catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(HttpSession aSession){
        aSession.invalidate();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
