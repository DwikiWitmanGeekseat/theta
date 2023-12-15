package co.flexidev.theta.repository;

import co.flexidev.theta.model.Person;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO person (id, created, creator, creator_id, name, storage_map, email, birth, password, active) " +
            "VALUES (:#{#person.id}, CURRENT_TIMESTAMP, :#{#person.creator}, :#{#person.creatorId}, :#{#person.name}, " +
            ":#{#person.storageMap}, :#{#person.email}, :#{#person.birth}, :#{#person.password}, :#{#person.active})")
    int insert(@Param("person") Person person);

    @Modifying
    @Transactional
    @Query(value = "UPDATE person SET edited=CURRENT_TIMESTAMP, editor=:#{#person.editor}, editor_id=:#{#person.editorId}, " +
            "name=:#{#person.name}, storage_map=:#{#person.storageMap}, email=:#{#person.email}, " +
            "birth=:#{#person.birth}, password=:#{#person.password}, active=:#{#person.active} " +
            "WHERE id=:#{#person.id}")
    int update(@Param("person") Person person);

    @Modifying
    @Transactional
    @Query("DELETE FROM person WHERE id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT COUNT(*) FROM person")
    long count();

    @Query("SELECT * FROM person where email = :email")
    Person findByEmail(@Param("email") String email);

    @Query("SELECT * FROM person")
    List<Person> findAll();

    @Query("SELECT * FROM person WHERE active = :active")
    List<Person> findByActive(@Param("active") Boolean active);

    @Modifying
    @Transactional
    @Query("DELETE FROM person WHERE active = false")
    int deleteInactivePerson();

    @Modifying
    @Transactional
    @Query("DELETE FROM person WHERE active = :active")
    int deleteByActive(@Param("active") Boolean active);

    @Query("SELECT count(email) FROM person")
    int getSum();

}
