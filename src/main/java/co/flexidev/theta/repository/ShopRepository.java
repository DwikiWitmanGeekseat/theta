package co.flexidev.theta.repository;

import co.flexidev.theta.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ShopRepository extends BaseRepository<Shop, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO shop (id, created, creator, creator_id, name, storage_map, active) " +
            "VALUES (:#{#shop.id}, CURRENT_TIMESTAMP, :#{#shop.creator}, :#{#shop.creatorId}, :#{#shop.name}, " +
            ":#{#shop.storageMap}, :#{#shop.active})")
    int insert(@Param("shop") Shop shop);

    @Modifying
    @Transactional
    @Query(value = "UPDATE shop SET edited=CURRENT_TIMESTAMP, editor=:#{#shop.editor}, " +
            "editor_id=:#{#shop.editorId}, name=:#{#shop.name}, " +
            "storage_map=:#{#shop.storageMap}, email=:#{#shop.email}, " +
            "birth=:#{#shop.birth}, password=:#{#shop.password}, active=:#{#shop.active} " +
            "WHERE id=:#{#shop.id}")
    int update(@Param("shop") Shop shop);

    Page<Shop> findAll(Pageable pageable);

}
