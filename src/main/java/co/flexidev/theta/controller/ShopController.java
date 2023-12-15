package co.flexidev.theta.controller;

import co.flexidev.theta.helper.Utility;
import co.flexidev.theta.model.Shop;
import co.flexidev.theta.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService service) {
        this.shopService = service;
    }

    @GetMapping("/paged/list")
    ResponseEntity<Page<Shop>> pagedList(
            @RequestParam(required = false, value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "sortDesc", defaultValue = "false") Boolean sortDesc,
            @RequestParam(required = false, value = "sortBy", defaultValue = "") String... sortBy
    ) {
        return ResponseEntity.ok(shopService.pagedList(PageRequest.of(page <= 0 ? 1 : page - 1, itemsPerPage, sortDesc ? Direction.DESC : Direction.ASC, sortBy.length > 0 ? sortBy : new String[]{"id"})));
    }

    @GetMapping(value = "/bulk/add") //
    public ResponseEntity<Long> bulkAdd(@RequestParam("limit") Integer limit) throws Exception {
        limit = Utility.coalesce(limit, 1000);
        Long start = System.currentTimeMillis();

        for (int i = 0; i < limit; i++) {
            shopService.save(new Shop(null, "store-" + i, "Store-" + i));
        }

        Long finish = System.currentTimeMillis();
        System.out.println(finish - start + "ms");
        return ResponseEntity.ok(finish - start);
    }

    @GetMapping(value = "/bulk/batch") //
    public ResponseEntity<Long> bulkBatch(@RequestParam("limit") Integer limit) {
        limit = Utility.coalesce(limit, 1000);
        long start = System.currentTimeMillis();

        List<Shop> shopList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            shopList.add(new Shop(null, "store-" + i, "Store-" + i));
        }

        shopService.saveAll(shopList);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start + "ms");
        return ResponseEntity.ok(finish - start);
    }
}
