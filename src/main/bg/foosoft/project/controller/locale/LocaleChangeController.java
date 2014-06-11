package bg.foosoft.project.controller.locale;

import bg.foosoft.project.locale.LocaleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by gharizanov on 14-5-30.
 */
@Controller
@RequestMapping("/locale")
public class LocaleChangeController {

    @Autowired
    private LocaleConfiguration messageBundle;

    /**
     * ReadAll
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }
}
