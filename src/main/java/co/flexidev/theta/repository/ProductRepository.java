package co.flexidev.theta.repository;


import co.flexidev.theta.model.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    @Query("SELECT * FROM product")
    List<Product> findAll();
}
