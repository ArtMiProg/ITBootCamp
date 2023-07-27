package usersItBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usersItBoot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
