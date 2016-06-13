package xml;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import xml.interceptors.AuthInterceptor;

/**
 * Created by Daniel on 6/11/2016.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //treba injektovati al jbg
        registry.addInterceptor(new AuthInterceptor());
    }
}