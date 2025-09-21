package com.sbs.qnaService.boundedContext.answer.service;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.repository.AnswerRepository;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import com.sbs.qnaService.global.exception.DataNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;

  public Answer create(Question question, String content) {
    Answer answer = Answer.builder()
        .content(content)
        .createDate(LocalDateTime.now())
        .question(question)
        .build();

    answerRepository.save(answer);

    return answer;
  }

  public Answer create(Question question, String content, SiteUser author) {
    Answer answer = Answer.builder()
        .content(content)
        .createDate(LocalDateTime.now())
        .question(question)
        .author(author)
        .build();

    answerRepository.save(answer);

    return answer;
  }

  public Answer getAnswer(Integer id) {
    Optional<Answer> answer = answerRepository.findById(id);
    if (answer.isPresent()) {
      return answer.get();
    } else {
      throw new DataNotFoundException("answer not found");
    }
  }

  public void modify(Answer answer, String content) {
    answer.setContent(content);
    answer.setModifyDate(LocalDateTime.now());
    answerRepository.save(answer);
  }
}
