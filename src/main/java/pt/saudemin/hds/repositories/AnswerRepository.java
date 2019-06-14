package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.entities.base.AnswerId;

@Repository
public interface AnswerRepository<T extends Answer> extends JpaRepository<T, AnswerId> {

}
