package SAPAdvertisements.repository;

import SAPAdvertisements.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);

//    @Query("SELECT u FROM Users u WHERE u.username = :username")
//    Users  getUserByUsername(@Param("username") String username);
}
