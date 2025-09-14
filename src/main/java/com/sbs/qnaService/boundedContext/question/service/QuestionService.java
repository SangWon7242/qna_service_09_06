package com.sbs.qnaService.boundedContext.question.service;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;

  public Page<Question> getList(int page) {
    // Pageable : 페이징 정보를 담는 객체
    Pageable pageable = PageRequest.of(page, 10); // 함 페이지당 10개씩
    return questionRepository.findAll(pageable);
  }

  public Question getQuestion(Integer id) {
    // SELECT * FROM question WHERE id = ?;
    Optional<Question> question = questionRepository.findById(id);
    if (question.isPresent()) {
      return question.get();
    } else {
      throw new DataNotFoundException("question not found");
    }
  }

  public void create(String subject, String content) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());
    questionRepository.save(q);
  }
}
