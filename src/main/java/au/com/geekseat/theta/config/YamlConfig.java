package au.com.geekseat.theta.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.configuration")
public class YamlConfig {
    private List<String> corsAllowedList = new ArrayList<>();
    private List<String> publicApiList = new ArrayList<>();

    public List<String> getCorsAllowedList() {
        return corsAllowedList;
    }

    public void setCorsAllowedList(List<String> corsAllowedList) {
        this.corsAllowedList = corsAllowedList;
    }

    public List<String> getPublicApiList() {
        return publicApiList;
    }

    public void setPublicApiList(List<String> publicApiList) {
        this.publicApiList = publicApiList;
    }
}
