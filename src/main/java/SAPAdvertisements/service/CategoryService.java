package SAPAdvertisements.service;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Categories readCategory(String category) throws CategoryNotFoundException {
       Categories categoriesFromDB = categoryRepository.findByCategoryName(category);
        if (categoriesFromDB == null){
            throw new CategoryNotFoundException(ConstantMessages.CATEGORY_NOT_FOUND_EXCEPTION);
        }
        return categoriesFromDB;
    }

    public List<Categories> readAllCategories() throws EmptyWishList {
        List<Categories> allCategoriesFromDB = categoryRepository.findAll();
        if (allCategoriesFromDB.size() == 0){
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }
        return allCategoriesFromDB;
    }


    public Categories createCategory(Categories categories) throws AlreadyExistsException {
        if (haveThisCategory(categories.getCategoryName())){
            throw new AlreadyExistsException(ConstantMessages.CATEGORY_ALREADY_EXIST_EXCEPTIONS);
        }
        return categoryRepository.save(categories);

    }

    public Categories updateCategoryData(String category, String data) throws AlreadyExistsException, CategoryNotFoundException {
        Categories categories = readCategory(category);
        if (haveThisCategory(data)){
            throw new AlreadyExistsException(ConstantMessages.CATEGORY_ALREADY_EXIST_EXCEPTIONS);
        }
        categories.setCategoryName(data);
        categoryRepository.save(categories);
        return categories;
    }


    public boolean haveThisCategory(String name){
        Categories categoryFromDB = categoryRepository.findByCategoryName(name);
        if (categoryFromDB.getCategoryName().equals(name)){
                return true;
        }
        return false;
    }

    public Categories findCategoryId(String name){
        return categoryRepository.findCategoryById(name);
    }

}
