package shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import shop.dto.category.ProductCreateDTO;
import shop.entities.CategoryEntity;
import shop.entities.ProductEntity;
import shop.repositories.ProductRepository;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> index(){
        var list = productRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductEntity> create(@RequestBody ProductCreateDTO model){
        ProductEntity product = new ProductEntity();
        product.setName(model.getName());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CategoryEntity[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/categories",
                        CategoryEntity[].class);
        CategoryEntity[] categories = response.getBody();
        for (CategoryEntity ca : categories){
            if(ca.getId() == model.getCategoryId())
                product.setCategory(ca);
        }
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}