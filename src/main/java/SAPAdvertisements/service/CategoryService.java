package SAPAdvertisements.service;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Categories readCategory(String category) throws CategoryNotFoundException {
        Categories categoryToReturn = null;
        List<Categories> categoriesFromDB = categoryRepository.findAll();
        for (Categories c : categoriesFromDB) {
            if (c.getCategoryName().equals(category)){
                categoryToReturn = c;
            }
        }
        if (categoryToReturn == null){
            throw new CategoryNotFoundException(ConstantMessages.CATEGORY_NOT_FOUND_EXCEPTION);
        }

        return categoryToReturn;
    }

    public List<Categories> readAllCategories() throws CategoryNotFoundException, EmptyWishList {
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

    public Categories updateCategoryData(String category, String data) throws  InvalidPropertyException, AlreadyExistsException, NonUpdateablePropertyException, CategoryNotFoundException {
        Categories categories = readCategory(category);
        if (haveThisCategory(data)){
            throw new AlreadyExistsException(ConstantMessages.CATEGORY_ALREADY_EXIST_EXCEPTIONS);
        }
        categories.setCategoryName(data);
        categoryRepository.save(categories);
        return categories;
    }


    public boolean haveThisCategory(String name){
        List<Categories> categoryFromDB = categoryRepository.findByCategoryName(name);
        for (Categories c:categoryFromDB) {
            if (c.getCategoryName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
