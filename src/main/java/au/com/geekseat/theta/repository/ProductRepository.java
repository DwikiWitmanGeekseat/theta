package au.com.geekseat.theta.repository;


import au.com.geekseat.theta.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import au.com.geekseat.theta.model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }
}
