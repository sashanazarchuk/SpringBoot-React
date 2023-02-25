package shop.dto.category;

import lombok.Data;

@Data
public class ProductImageCreateDTO {
    private String Name;
    private int ProductId;
    private int Priority;
}