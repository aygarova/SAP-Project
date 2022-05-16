package SAPAdvertisements.controller;

import SAPAdvertisements.dtos.CategoryDto;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.util.Converters;
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

    @Autowired
    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
            return status(CREATED).body(categoryService.createCategory(Converters.convertToCategoryEntity(categoryDto)));
    }

    @PatchMapping(value = "/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categories> patchCategory(@PathVariable("category") String category, @RequestBody String data) throws AlreadyExistsException, CategoryNotFoundException {
            return status(OK).body(categoryService.updateCategoryData(category,data));
    }
}
