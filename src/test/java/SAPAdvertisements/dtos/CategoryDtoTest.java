package SAPAdvertisements.dtos;

import SAPAdvertisements.models.Categories;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CategoryDtoTest {
    private CategoryDto categoryDto;

    @Before
    public void setup(){
        this.categoryDto = new CategoryDto( "Book");
    }

    @Test
    public void testEmptyConstructor(){
        CategoryDto categories = new CategoryDto();
    }

    @Test
    public void testGetAndMethodsCategoryName(){
        categoryDto.setCategoryName("Toys");
        Assert.assertEquals("Toys", categoryDto.getCategoryName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCategoryNameBlank(){
        categoryDto.setCategoryName("");
       // Assert.assertEquals(IllegalArgumentException.class, categoryDto.getCategoryName());
    }
}