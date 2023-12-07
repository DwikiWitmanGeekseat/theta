package co.flexidev.theta;

import co.flexidev.theta.config.YamlConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YamlConfigIntegrationTest {

    @Autowired
    private YamlConfig yamlConfig;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertThat(yamlConfig.getName()).isEqualTo("application.yml");
        assertThat(yamlConfig.getAliases()).containsExactly("properties.yml", "env.yml");
    }
}
