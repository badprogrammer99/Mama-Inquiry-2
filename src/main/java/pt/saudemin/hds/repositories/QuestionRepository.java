package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.saudemin.hds.entities.base.Question;

public interface QuestionRepository<T extends Question> extends JpaRepository<T, Long> {
}
