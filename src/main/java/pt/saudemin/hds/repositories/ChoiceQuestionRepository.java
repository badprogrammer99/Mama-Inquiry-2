package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.saudemin.hds.entities.ChoiceQuestion;

@Repository
public interface ChoiceQuestionRepository extends JpaRepository<ChoiceQuestion, Long> {

}
