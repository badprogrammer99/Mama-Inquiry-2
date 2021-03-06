package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.saudemin.hds.entities.AnswerChoice;

@Repository
public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Long> {
}
