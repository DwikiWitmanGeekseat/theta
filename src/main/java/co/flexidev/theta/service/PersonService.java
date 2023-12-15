package co.flexidev.theta.service;

import co.flexidev.theta.dao.PersonDao;
import co.flexidev.theta.helper.DataTablesResponse;
import co.flexidev.theta.model.Person;
import co.flexidev.theta.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDao personDao;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder, PersonDao personDao) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDao = personDao;
    }

    @Transactional
    public Person savePerson(Person person) throws SQLException {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return save(person);
    }

    @Transactional
    public Person trxDemo(Person person) throws SQLException {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        // throw new SQLException("Throwing exception for demoing rollback");
        return save(person);
    }

    @Transactional(readOnly = true)
    public Iterable<Person> findAll() {
        LOGGER.info("Fetching all persons");
        return sanitiseAll(personRepository.findAll());
    }

//    @Transactional(readOnly = true)
//    public int savePersonRole(Long personId, Long rolesId)  {
//        LOGGER.info("Save person roles");
//        return personRepository.savePersonRole(personId, rolesId);
//    }

    @Transactional(readOnly = true)
    public DataTablesResponse<Person> datatables(Long page, Long itemsPerPage, List<String> sortBy, List<Boolean> sortDesc) {
        List<Person> list = (List<Person>) sanitiseAll(personDao.listDataTables(page, itemsPerPage, sortBy, sortDesc));
        Long rowCount = personDao.rowCountDatatables();
        return new DataTablesResponse<>(list, rowCount);
    }

    public Iterable<Person> findByActive(Boolean active) {
        return sanitiseAll(personRepository.findByActive(active));
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
        return sanitise(personRepository.findByEmail(email));
    }

    @Transactional()
    public Person save(Person person) throws SQLException {
        if (person.getId() == null) {
            person.setId(personRepository.sequence());
            int rowsInserted = personRepository.insert(person);

            if (rowsInserted > 0) {
                return sanitise(person);
            } else {
                LOGGER.error("SQL Error when creating "+ person.getName());
                throw new SQLException("SQL Error when creating "+ person.getName());
            }
        } else {
            int rowsUpdated = personRepository.update(person);

            if (rowsUpdated > 0) {
                return sanitise(person);
            } else {
                LOGGER.error("SQL Error when updating "+ person.getName());
                throw new SQLException("SQL Error when updating "+ person.getName());
            }
        }
    }

    @Transactional()
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public static Person sanitise(Person person) {
        if (person != null) {
            person.setCreatorId(null);
            person.setCreator(null);
            person.setCreated(null);
            person.setEditorId(null);
            person.setEditor(null);
            person.setEdited(null);
            person.setPassword(null);
        }
        return person;
    }

    public static Iterable<Person> sanitiseAll(Iterable<Person> personList) {
        List<Person> sanitizedList = new ArrayList<>();

        for (Person person : personList) {
            sanitizedList.add(sanitise(person));
        }

        return sanitizedList;
    }
}
