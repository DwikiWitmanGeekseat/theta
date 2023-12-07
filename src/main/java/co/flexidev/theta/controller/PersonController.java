package co.flexidev.theta.controller;

import co.flexidev.theta.model.Person;
import co.flexidev.theta.model.Role;
import co.flexidev.theta.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person person) {
        if (person.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.save(person));
    }

    @GetMapping
    public ResponseEntity<Iterable<Person>> findAll() {
        Iterable<Person> personList = personService.findAll();
        for (Person person : personList) {
            Long personId = person.getId();
            List<Role> roles = personService.findRolesByPersonId(personId);
            person.setRoles(roles); // Set roles for the current person
        }
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/datatables")
    public ResponseEntity<?> datatables(
            @RequestParam(required = false, value = "itemsPerPage", defaultValue = "10") Long itemsPerPage,
            @RequestParam(required = false, value = "page", defaultValue = "0") Long page,
            @RequestParam(required = false, value = "sortBy", defaultValue = "") List<String> sortBy,
            @RequestParam(required = false, value = "sortDesc", defaultValue = "false") List<Boolean> sortDesc) {
        return ResponseEntity.ok().body(personService.datatables(page, itemsPerPage, sortBy, sortDesc));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(personService.count());
    }

    @GetMapping("/email")
    public ResponseEntity<Person> findByEmail(@RequestParam String email) {
        Person person = personService.findByEmail(email);
        Long personId = person.getId();
        List<Role> roles = personService.findRolesByPersonId(personId);
        person.setRoles(roles);
        return ResponseEntity.ok(person);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Person> save(@RequestBody @Valid Person person) {
        Person dbPerson = personService.savePerson(person);
//        for (Role role : dbPerson.getRoles()) {
//            personService.savePersonRole(dbPerson.getId(), role.getId());
//        }
        return ResponseEntity.ok(dbPerson);
    }
    @GetMapping("/active")
    public ResponseEntity<Iterable<Person>> findByActive(@RequestParam Boolean active) {
        return ResponseEntity.ok(personService.findByActive(active));
    }

    @PostMapping("/trx/demo")
    public ResponseEntity<Person> trxDemo(@RequestBody @Valid Person person) throws Exception {
        return ResponseEntity.ok(personService.trxDemo(person));
    }

    @PutMapping("/edit")
    public ResponseEntity<Person> edit(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/inactive/delete")
    public ResponseEntity<Integer> deleteInactivePerson() {
        return ResponseEntity.ok(personService.deleteInactivePerson());
    }
}