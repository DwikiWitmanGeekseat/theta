package au.com.geekseat.theta.controller;

import au.com.geekseat.theta.model.Product;
import au.com.geekseat.theta.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
