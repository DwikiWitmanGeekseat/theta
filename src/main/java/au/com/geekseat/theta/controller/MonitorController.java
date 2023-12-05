package au.com.geekseat.theta.controller;

import au.com.geekseat.theta.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {
    Logger LOGGER = LogManager.getLogger(MonitorController.class);

    private final PersonRepository personRepository;

    public MonitorController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Ok";
    }

    @GetMapping("/ping/db")
    public String pingdb() {
        String sqlcon = "OK";

        // check SQL connection
        try {
            Integer counter = personRepository.getSum();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            sqlcon = e.getMessage();
        }

        return "SQL Connection : " + sqlcon;
    }
}
