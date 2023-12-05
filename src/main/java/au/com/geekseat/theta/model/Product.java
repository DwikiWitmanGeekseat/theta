package au.com.geekseat.theta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Product extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonIgnoreProperties("additionalProductList")
    private Shop shop;

    @Column(name = "slug", nullable = false, length = 255)
    private String slug;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
