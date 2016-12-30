package ovh.classregister.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ovh.classregister.users.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
