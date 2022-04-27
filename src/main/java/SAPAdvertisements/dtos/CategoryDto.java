package SAPAdvertisements.dtos;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

    @NotBlank(message = "Category name can't be empty")
    private String categoryName;

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDto() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
