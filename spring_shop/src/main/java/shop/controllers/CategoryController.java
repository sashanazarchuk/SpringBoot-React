package shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.dto.category.CategoryCreateDTO;
import shop.dto.category.CategoryItemDTO;
import shop.dto.category.UpdateCategoryDTO;
import shop.entities.CategoryEntity;
import shop.interfaces.CategoryService;
import shop.mapper.CategoryMapper;
import shop.repositories.CategoryRepository;
import shop.storage.StorageService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        var list = categoryRepository.findAll();
        var model = categoryMapper.CategoryItemsByCategories(list);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    //Роблю пост запит який отримує дані у форматі multipart/form-data для створення категорії та фото
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO model) {
        //створюю новий запис категорії у бд
        var fileName = storageService.saveMultipartFile(model.getFile());
        //створюю новий об'єкт що містить дані нової категорії
        CategoryEntity category = categoryMapper.CategoryByCreateDTO(model);
        //добавляю фото
        category.setImage(fileName);
        //зберігаю категорію
        categoryRepository.save(category);
        //реалізую маппінг даних
        var result = categoryMapper.CategoryItemByCategory(category);
        //створюю об'єкт зі статусом 201 який відправляється клієнту
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<CategoryItemDTO> update(@PathVariable("id") int categoryId,
                                                  @RequestBody UpdateCategoryDTO model) {
        var result = categoryService.update(categoryId, model);
        if(result!=null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity<>("Категорію видалено.", HttpStatus.OK);
    }
}
