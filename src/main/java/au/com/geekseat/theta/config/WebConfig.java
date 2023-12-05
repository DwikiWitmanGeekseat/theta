package au.com.geekseat.theta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HTTPCorrelationInterceptor httpCorrelationInterceptor;

    public WebConfig(HTTPCorrelationInterceptor httpCorrelationInterceptor) {
        this.httpCorrelationInterceptor = httpCorrelationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpCorrelationInterceptor);
    }


}