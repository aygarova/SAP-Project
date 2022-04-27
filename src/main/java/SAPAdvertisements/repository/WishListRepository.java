package SAPAdvertisements.repository;

import SAPAdvertisements.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    @Query("SELECT w FROM WishList w WHERE w.userID.user_id IN (:user_id)")
    List<WishList> findByUserID(int user_id);
}

