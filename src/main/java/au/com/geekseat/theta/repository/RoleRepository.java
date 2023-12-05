package au.com.geekseat.theta.repository;

import au.com.geekseat.theta.helper.Utility;
import au.com.geekseat.theta.model.Person;
import au.com.geekseat.theta.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Role save(Role role) {
        if (role.getId() == null) {
            // If id is null, perform an insert
            String insertSql = "INSERT INTO role (created, creator, creator_id, storage_map, active, name) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    insertSql,
                    role.getCreated(),
                    role.getCreator(),
                    role.getCreatorId(),
                    role.getStorageMap(),
                    role.getActive(),
                    role.getName()
            );
        } else {
            // If id is not null, perform an update
            String updateSql = "UPDATE role SET edited=?, editor=?, editor_id=?, storage_map=?, active=?, name=? WHERE id=?";
            jdbcTemplate.update(
                    updateSql,
                    role.getEdited(),
                    role.getEditor(),
                    role.getEditorId(),
                    Utility.gson.toJson(role.getMap()),
                    role.getActive(),
                    role.getName(),
                    role.getId()
            );
        }
        return role;
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM role";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Role.class));
    }

    public Role findByName(String name) {
        String sql = "SELECT * FROM role WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name}, BeanPropertyRowMapper.newInstance(Role.class));
    }
}
