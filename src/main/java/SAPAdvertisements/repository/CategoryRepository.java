package SAPAdvertisements.repository;

import SAPAdvertisements.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {
    Categories findByCategoryName(String name);

    @Query("SELECT c FROM Categories c WHERE c.categoryName IN (:name)")
    Categories findCategoryById(String name);
}
