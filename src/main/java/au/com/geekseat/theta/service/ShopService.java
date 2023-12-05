package au.com.geekseat.theta.service;

import au.com.geekseat.theta.model.Shop;
import au.com.geekseat.theta.repository.PersonRepository;
import au.com.geekseat.theta.repository.ShopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository repository) {
        this.shopRepository = repository;
    }

    public Page<Shop> pagedList(Pageable pageable){
        return shopRepository.findAll(pageable);
    }

    public Shop save(Shop shop){
        return shopRepository.save(shop);
    }

    public List<Shop> saveAll(List<Shop> shopList){
        return shopRepository.saveAll(shopList);
    }
}
