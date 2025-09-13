package com.sbs.qnaService.boundedContext.answer.service;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.repository.AnswerRepository;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;

  public Answer create(Question question, String content) {
    /*
    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    answer.setQuestion(question);
    */

    Answer answer = Answer.builder()
        .content(content)
        .createDate(LocalDateTime.now())
        .question(question)
        .build();

    answerRepository.save(answer);

    return answer;
  }
}
