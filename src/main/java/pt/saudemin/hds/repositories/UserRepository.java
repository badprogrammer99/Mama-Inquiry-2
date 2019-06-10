package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.saudemin.hds.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPersonalId(Integer personalId);
    void deleteByPersonalId(Integer personalId);
}
