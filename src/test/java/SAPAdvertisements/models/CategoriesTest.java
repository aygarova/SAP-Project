package SAPAdvertisements.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CategoriesTest {
    private Categories categories;

    @Before
    public void setup(){
        this.categories = new Categories(1, "Book");
    }

    @Test
    public void testEmptyConstructor(){
        Categories categories = new Categories();
    }

    @Test
    public void testConstructorWithId(){
        Categories categoriesWithCategoryIDInConstructor = new Categories(1);
    }

    @Test
    public void testConstructorWithName(){
        Categories categories = new Categories("Shoes");
    }


    @Test
    public void testGetAndMethodsCategoryId(){
        categories.setCategory_id(1);
        Assert.assertEquals(1, categories.getCategory_id());
    }

    @Test
    public void testGetAndMethodsCategoryName(){
        categories.setCategoryName("Toys");
        Assert.assertEquals("Toys", categories.getCategoryName());
    }

}