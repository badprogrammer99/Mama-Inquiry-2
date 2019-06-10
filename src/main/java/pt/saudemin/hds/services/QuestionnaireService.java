package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.QuestionnaireDTO;

import java.util.List;

public interface QuestionnaireService {
    List<QuestionnaireDTO> getAll();
    QuestionnaireDTO getById(long id);
    QuestionnaireDTO create(QuestionnaireDTO questionnaireDTO);
    QuestionnaireDTO update(QuestionnaireDTO questionnaireDTO);
    Boolean delete(long id);
}
