package personal.project.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.project.users.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
