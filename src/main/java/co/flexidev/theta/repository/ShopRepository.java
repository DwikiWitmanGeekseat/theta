package co.flexidev.theta.repository;

import co.flexidev.theta.helper.Utility;
import co.flexidev.theta.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ShopRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Shop save(Shop shop) {
        if (shop.getId() == null) {
            // If id is null, perform an insert
            String insertSql = "INSERT INTO shop (created, creator, creator_id, storage_map, active, name, slug) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    insertSql,
                    shop.getCreated(),
                    shop.getCreator(),
                    shop.getCreatorId(),
                    shop.getStorageMap(),
                    shop.getActive(),
                    shop.getName(),
                    shop.getSlug()
            );
        } else {
            // If id is not null, perform an update
            String updateSql = "UPDATE shop SET edited=?, editor=?, editor_id=?, storage_map=?, active=?, name=?, slug=? WHERE id=?";
            jdbcTemplate.update(
                    updateSql,
                    shop.getEdited(),
                    shop.getEditor(),
                    shop.getEditorId(),
                    Utility.gson.toJson(shop.getMap()),
                    shop.getActive(),
                    shop.getName(),
                    shop.getSlug(),
                    shop.getId()
            );
        }
        return shop;
    }

    public Page<Shop> findAll(Pageable pageable) {
        String sql = "SELECT * FROM shop";
        List<Shop> resultList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Shop.class));

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Shop> pageList;

        if (resultList.size() < startItem) {
            pageList = List.of();
        } else {
            int toIndex = Math.min(startItem + pageSize, resultList.size());
            pageList = resultList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageList, pageable, resultList.size());
    }

    public List<Shop> saveAll(List<Shop> shopList) {
        for (Shop shop : shopList) {
            save(shop);
        }
        return shopList;
    }
}
