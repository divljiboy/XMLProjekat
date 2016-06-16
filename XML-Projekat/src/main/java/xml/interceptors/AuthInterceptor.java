package xml.interceptors;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xml.Constants;
import xml.model.Korisnik;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Daniel on 6/11/2016.
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //get method anotation values for role
        //add token to response header
        String token = request.getHeader("x-auth-token");
        response.addHeader("x-auth-token",token);

        if (handler instanceof HandlerMethod) {
            String[] roles = null;
            HandlerMethod method = (HandlerMethod) handler;
            RolesAllowed rolesAllowed = method.getMethodAnnotation(RolesAllowed.class);
            if(rolesAllowed == null){
                if(method.getClass().isAnnotationPresent(PermitAll.class)){
                    return true;
                }
            }
            if(rolesAllowed != null)
                roles = rolesAllowed.value();
            else
                return true;

            if(containsRole(roles,Constants.Gradjanin)){
                return true;
            }

            TokenHandler tokenHandler = new TokenHandler();
            Korisnik korisnik = tokenHandler.parseUserFromToken(token);
            if(korisnik != null) {
                if (containsRole(roles, korisnik.getUloga()))
                    return true;
            }
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized Request");
            return false;
        }

        return true;
    }


    private boolean containsRole(String[] roles,String role){
        for(String r : roles)
            if(r.equals(role))
                return true;
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println(response.getHeader("x-auth-token"));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println(response.getHeader("x-auth-token"));
    }
}
