package SAPAdvertisements.repository;

import SAPAdvertisements.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {
    List<Categories> findByCategoryName(String name);
}
