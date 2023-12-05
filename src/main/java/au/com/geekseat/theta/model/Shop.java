package au.com.geekseat.theta.model;

import au.com.geekseat.theta.helper.Utility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Shop extends BaseModel {

    @Size(min = 3, max = 128)
    @Column(name = "slug", nullable = false, length = 255)
    private String slug;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Transient
    private List<Product> additionalProductList = new ArrayList<>();

    public Shop() {
        additionalProductList.add(new Product());
        additionalProductList.add(new Product());
        additionalProductList.add(new Product());

        Map<String, String> level5 = new HashMap<>();
        Map<String, Map> level4 = new HashMap<>();
        Map<String, Map> level3 = new HashMap<>();
        Map<String, Map> level2 = new HashMap<>();
        Map<String, Map> level1 = new HashMap<>();

        level4.put("level5", level5);
        level3.put("level4", level4);
        level2.put("level3", level3);
        level1.put("level2", level2);
//        map.put("level1", level1);
        Map<String, Object> _level1 = new HashMap<>();
        _level1.put("level1", level1);
        storageMap = Utility.gson.toJson(_level1, Utility.typeMapOfStringObject);
    }

    public Shop(Long id, String slug, String name) {
        this.setId(id);
        this.slug = slug;
        this.name = name;
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

    public List<Product> getAdditionalProductList() {
        return additionalProductList;
    }

    public void setAdditionalProductList(List<Product> additionalProductList) {
        this.additionalProductList = additionalProductList;
    }

}
