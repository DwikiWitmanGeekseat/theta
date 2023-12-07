package co.flexidev.theta;

import co.flexidev.theta.config.YamlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThetaApplication implements CommandLineRunner {

	@Autowired
	private YamlConfig yamlConfig;

	public static void main(String[] args) {
		SpringApplication.run(ThetaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("YAML Properties " + yamlConfig.toString());
	}
}
