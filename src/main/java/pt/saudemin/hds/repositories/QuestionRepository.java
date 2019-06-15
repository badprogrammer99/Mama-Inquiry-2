package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.saudemin.hds.entities.base.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
