package com.sbs.qnaService.boundedContext.answer.service;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.repository.AnswerRepository;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
