package bg.foosoft.project.interceptor;

import bg.foosoft.project.util.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Kayne on 14-1-13.
 */
@Component
@Scope("request")
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfo mUserInfo;

    @Override
    public boolean preHandle(HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse, Object o) throws Exception {

        if(aHttpServletRequest.getRequestURI().contains(".js") ||
                aHttpServletRequest.getRequestURI().contains(".css") ||
                aHttpServletRequest.getRequestURI().contains(".png") ||
                aHttpServletRequest.getRequestURI().contains(".json") ||
                aHttpServletRequest.getRequestURI().contains(".gif") ||
                aHttpServletRequest.getRequestURI().contains("/register")){
            return true;
        }

        if(mUserInfo.getId() != null){
            return true;
        } /*
        System.out.println(aHttpServletRequest.getRequestURI());
        if(!aHttpServletRequest.getRequestURI().contains("/newsfeed/login")){
            aHttpServletResponse.sendRedirect("/newsfeed/login");
            return false;
        }*/

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse, Object o, ModelAndView aModelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse, Object o, Exception e) throws Exception {

    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo aAUserInfo) {
        mUserInfo = aAUserInfo;
    }


}
