package SAPAdvertisements.repository;

import SAPAdvertisements.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users  getUserByUsername(@Param("username") String username);
}
