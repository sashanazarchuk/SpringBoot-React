package shop.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import shop.dto.category.CategoryCreateDTO;
import shop.dto.category.CategoryItemDTO;
import shop.entities.CategoryEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T18:28:32+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity CategoryByCreateDTO(CategoryCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName( dto.getName() );
        categoryEntity.setDescription( dto.getDescription() );

        return categoryEntity;
    }

    @Override
    public CategoryItemDTO CategoryItemByCategory(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryItemDTO categoryItemDTO = new CategoryItemDTO();

        categoryItemDTO.setId( entity.getId() );
        categoryItemDTO.setName( entity.getName() );
        categoryItemDTO.setImage( entity.getImage() );
        categoryItemDTO.setDescription( entity.getDescription() );

        return categoryItemDTO;
    }

    @Override
    public List<CategoryItemDTO> CategoryItemsByCategories(List<CategoryEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryItemDTO> list = new ArrayList<CategoryItemDTO>( entities.size() );
        for ( CategoryEntity categoryEntity : entities ) {
            list.add( CategoryItemByCategory( categoryEntity ) );
        }

        return list;
    }
}
