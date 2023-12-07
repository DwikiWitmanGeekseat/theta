package co.flexidev.theta.repository;

import co.flexidev.theta.helper.Utility;
import co.flexidev.theta.model.Person;
import co.flexidev.theta.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            // If id is null, perform an insert
            String insertSql = "INSERT INTO person (created, creator, creator_id, name, storage_map, email, birth, password, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(2, person.getCreator());
                ps.setString(3, person.getCreatorId());
                ps.setString(4, person.getName());
                ps.setString(5, Utility.gson.toJson(person.getStorageMap()));
                ps.setString(6, person.getEmail());
                ps.setDate(7, person.getBirth() != null ? Date.valueOf(person.getBirth()):null);
                ps.setString(8, person.getPassword());
                ps.setBoolean(9, person.getActive());
                return ps;
            }, keyHolder);

            // Get the generated id and set it in the Person object
            Number generatedId = keyHolder.getKey();
            person.setId(generatedId.longValue());
        } else {
            // If id is not null, perform an update
            String updateSql = "UPDATE person SET edited=?, editor=?, editor_id=?, name=?, storage_map=?, email=?, birth=?, password=?, active=? WHERE id=?";
            jdbcTemplate.update(
                    updateSql,
                    person.getEdited(),
                    person.getEditor(),
                    person.getEditorId(),
                    person.getName(),
                    Utility.gson.toJson(person.getStorageMap()),
                    person.getEmail(),
                    person.getBirth(),
                    person.getPassword(),
                    person.getActive(),
                    person.getId()
            );
        }
        return person;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Long count() {
        String sql = "SELECT COUNT(*) FROM person";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Person findByEmail(String email) {
        String sql = "SELECT * FROM person WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, BeanPropertyRowMapper.newInstance(Person.class));
    }

    public List<Role> findRolesByPersonId(Long personId) {
        String sql = "SELECT r.id as id, r.name as name FROM person_roles pr INNER JOIN role r ON pr.roles_id = r.id WHERE pr.person_id = ?";
        return jdbcTemplate.query(sql, new Object[]{personId}, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        });
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
    }

    public List<Person> findByActive(Boolean active) {
        String sql = "SELECT * FROM person WHERE active = ?";
        return jdbcTemplate.query(sql, new Object[]{active}, BeanPropertyRowMapper.newInstance(Person.class));
    }

    public int deleteInactivePerson() {
        String sql = "DELETE FROM person WHERE active = false";
        return jdbcTemplate.update(sql);
    }

    public int deleteByActive(Boolean active) {
        String sql = "DELETE FROM person WHERE active = ?";
        return jdbcTemplate.update(sql, active);
    }

    public int getSum() {
        String sql = "SELECT count(email) FROM person";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

//    public int savePersonRole(Long personId, Long rolesId) {
//        int result = 0;
//        if (countRoles(personId, rolesId) == null) {
//            // If id is null, perform an insert
//            String insertSql = "INSERT INTO person_roles (person_id, roles_id) VALUES (?, ?)";
//            result = jdbcTemplate.update(
//                    insertSql,
//                    personId,
//                    rolesId
//            );
//        } else {
//            // If id is not null, perform an update
//            String updateSql = "UPDATE person_roles SET roles_id = ? WHERE person_id = ? AND roles_id = ?";
//            result = jdbcTemplate.update(
//                    updateSql,
//                    rolesId,    // Replace rolesId with the new value
//                    personId,
//                    rolesId     // Leave rolesId as is (current rolesId)
//            );
//        }
//        return result;
//    }
//
//    public Integer countRoles(Long personId, Long rolesId) {
//        String sql = "SELECT COUNT(*) FROM person_roles WHERE person_id = ? AND roles_id = ?";
//        return jdbcTemplate.queryForObject(sql, Integer.class, personId, rolesId);
//    }

}
