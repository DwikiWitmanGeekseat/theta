package co.flexidev.theta.config;

import co.flexidev.theta.factory.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class YamlConfig {
    private String name;
    private List<String> aliases;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public String toString() {
        return "YamlConfig{" +
                "name='" + name + '\'' +
                ", aliases=" + aliases +
                '}';
    }
}
