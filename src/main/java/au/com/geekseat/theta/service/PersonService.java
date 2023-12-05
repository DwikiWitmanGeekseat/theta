package au.com.geekseat.theta.service;

import au.com.geekseat.theta.dao.PersonDao;
import au.com.geekseat.theta.helper.DataTablesResponse;
import au.com.geekseat.theta.model.Person;
import au.com.geekseat.theta.model.Role;
import au.com.geekseat.theta.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class PersonService {

    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDao personDao;
    private final RoleService roleService;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder, PersonDao personDao, RoleService roleService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDao = personDao;
        this.roleService = roleService;
    }

    @Transactional
    public Person savePerson(Person person) {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Transactional
    public Person trxDemo(Person person) throws SQLException {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Role role = new Role();
        role.setName("Test");
        roleService.save(role);
        // throw new SQLException("Throwing exception for demoing rollback");
        return personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Iterable<Person> findAll() {
        LOGGER.info("Fetching all persons");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<Person> datatables(Long page, Long itemsPerPage, List<String> sortBy, List<Boolean> sortDesc) {
        List<Person> list = personDao.listDataTables(page, itemsPerPage, sortBy, sortDesc);
        Long rowCount = personDao.rowCountDatatables();
        return new DataTablesResponse<>(list, rowCount);
    }

    public Iterable<Person> findByActive(Boolean active) {
        return personRepository.findByActive(active);
    }

    public int deleteInactivePerson() {
        // demo modifying query
        // int response = personRepository.deleteByActive(false);
        return personRepository.deleteInactivePerson();
    }

    @Transactional(readOnly = true)
    public Long count() {
        return personRepository.count();
    }

    @Transactional(readOnly = true)
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Transactional()
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional()
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
