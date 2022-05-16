package SAPAdvertisements.repository;

import SAPAdvertisements.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

    List<Users>  getUserByUsername(String username);
    List<Users>  getByEmail(String email);
}
