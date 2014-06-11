package bg.foosoft.project.controller;


import bg.foosoft.project.dao.UserDAO;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Kayne on 14-1-11.
 */
@Controller
@RequestMapping("/")
public class RegistrationController {

    @Autowired
    private UserDAO mUserDAO;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User aNewUser){
        mUserDAO.registerUser(aNewUser);
        return Urls.LOGIN_PAGE;
    }

    public UserDAO getUserDAO() {
        return mUserDAO;
    }

    public void setUserDAO(UserDAO aUserDAO) {
        mUserDAO = aUserDAO;
    }
}
