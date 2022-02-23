package SAPAdvertisements.controller;

import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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
    public ResponseEntity<Categories> getCategory(@PathVariable("category") String category) {
        try {
            return status(OK).body(categoryService.readCategory(category));
        }catch (CategoryNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Categories>> getAllCategories() {
        try {
            return status(OK).body(categoryService.readAllCategories());
        }catch (CategoryNotFoundException | EmptyWishList e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        try {
            return status(CREATED).body(categoryService.createCategory(category));
        }  catch (AlreadyExistsException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categories> patchCategory(@PathVariable("category") String category, @RequestBody String data) {
        try {
            return status(OK).body(categoryService.updateCategoryData(category,data));
        }catch (InvalidPropertyException | AlreadyExistsException | NonUpdateablePropertyException | CategoryNotFoundException e){
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }
}
