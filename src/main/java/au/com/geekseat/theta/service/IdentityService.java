package au.com.geekseat.theta.service;

import au.com.geekseat.theta.model.Person;
import au.com.geekseat.theta.model.Role;
import au.com.geekseat.theta.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdentityService implements UserDetailsService  {
    Logger LOGGER = LogManager.getLogger(IdentityService.class);
    private final PersonRepository personRepository;

    public IdentityService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            LOGGER.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else if (!person.getActive()) {
            LOGGER.error("Failed to authenticate since user account is inactive");
            throw new UsernameNotFoundException("User is inactive");
        } else {
            LOGGER.info("User found in the database: {}", email);
        }

        List<Role> roles = personRepository.findRolesByPersonId(person.getId());
        person.setRoles(roles);
        return new Person(person);
    }
}
