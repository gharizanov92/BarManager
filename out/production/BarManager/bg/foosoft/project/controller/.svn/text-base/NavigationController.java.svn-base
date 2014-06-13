package bg.foosoft.project.controller;

import bg.foosoft.project.util.Urls;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Kayne on 14-1-11.
 */
@Controller
@Scope("request")
@RequestMapping(value = "/")
public class NavigationController{

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap aMap){
        return "index";
    }

    @RequestMapping(value = "/home")
    public String home(ModelMap aMap) {
        return "home";
    }

    @RequestMapping(value = "/register")
    public String register(ModelMap aMap){
        return "register";
    }
}
