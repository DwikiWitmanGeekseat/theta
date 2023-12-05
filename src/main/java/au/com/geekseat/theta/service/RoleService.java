package au.com.geekseat.theta.service;

import au.com.geekseat.theta.model.Person;
import au.com.geekseat.theta.model.Role;
import au.com.geekseat.theta.repository.PersonRepository;
import au.com.geekseat.theta.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.*;


@Service
@Transactional
public class RoleService {
    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    public RoleService(RoleRepository roleRepository, PersonRepository personRepository) {
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
    }

    public void addRoleToPerson(String email, String roleName) {
        LOGGER.info("Adding role {} to person {}", roleName, email);
        Person person = personRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        person.getRoles().add(role);
    }

    @Transactional(propagation = REQUIRED)
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
