package SAPAdvertisements.controller;

import SAPAdvertisements.dtos.CategoryDto;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/category")
public class CategoriesController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired

    public CategoriesController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{category}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Categories> getCategory(@PathVariable("category") String category) throws CategoryNotFoundException {
            return status(OK).body(categoryService.readCategory(category));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Categories>> getAllCategories() throws EmptyWishList, CategoryNotFoundException {
            return status(OK).body(categoryService.readAllCategories());
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categories> createCategory(@Valid @RequestBody CategoryDto categoryDto) throws AlreadyExistsException {
            return status(CREATED).body(categoryService.createCategory(convertToCategoryEntity(categoryDto)));
    }

    @PatchMapping(value = "/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categories> patchCategory(@PathVariable("category") String category, @RequestBody String data) throws AlreadyExistsException, InvalidPropertyException, CategoryNotFoundException, NonUpdateablePropertyException {
            return status(OK).body(categoryService.updateCategoryData(category,data));
    }

    private CategoryDto convertToCategoryDto(Categories category) {
        return modelMapper.map(category,CategoryDto.class);
    }

    private Categories convertToCategoryEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Categories.class);
    }
}
