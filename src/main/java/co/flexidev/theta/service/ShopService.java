package co.flexidev.theta.service;

import co.flexidev.theta.model.Shop;
import co.flexidev.theta.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ShopService {
    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository repository) {
        this.shopRepository = repository;
    }

    public Page<Shop> pagedList(Pageable pageable){
        return shopRepository.findAll(pageable);
    }

    public Shop save(Shop shop) throws SQLException {
        if (shop.getId() == null) {
            shop.setId(shopRepository.sequence());
            int rowsInserted = shopRepository.insert(shop);

            if (rowsInserted > 0) {
                return (shop);
            } else {
                LOGGER.error("SQL Error when creating "+ shop.getName());
                throw new SQLException("SQL Error when creating "+ shop.getName());
            }
        } else {
            int rowsUpdated = shopRepository.update(shop);

            if (rowsUpdated > 0) {
                return (shop);
            } else {
                LOGGER.error("SQL Error when updating "+ shop.getName());
                throw new SQLException("SQL Error when updating "+ shop.getName());
            }
        }
    }

    public List<Shop> saveAll(List<Shop> shopList){
        for (Shop shop : shopList) {
            shopRepository.save(shop);
        }
        return shopList;
    }

}
