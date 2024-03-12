package test.muruna.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.muruna.com.repositories.entities.Users;


import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    boolean existsByEmail(String email);
}
