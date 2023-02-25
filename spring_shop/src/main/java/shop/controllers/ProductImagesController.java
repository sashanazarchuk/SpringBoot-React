package shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import shop.dto.category.ProductImageCreateDTO;
import shop.entities.ProductEntity;
import shop.entities.ProductImageEntity;
import shop.repositories.ProductImageRepository;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/products_images")
public class ProductImagesController {
    private final ProductImageRepository productImageRepository;
    @GetMapping
    public ResponseEntity<List<ProductImageEntity>> index(){
        var list = productImageRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductImageEntity> create(@RequestBody ProductImageCreateDTO model){
        ProductImageEntity productImage = new ProductImageEntity();
        productImage.setName(model.getName());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductEntity[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/products",
                        ProductEntity[].class);
        ProductEntity[] products = response.getBody();
        for (ProductEntity pe : products){
            if (pe.getId() == model.getProductId())
                productImage.setProduct(pe);
        }
        productImage.setPriority(model.getPriority());
        productImageRepository.save(productImage);
        return new ResponseEntity<>(productImage, HttpStatus.CREATED);
    }
}