package com.sbs.qnaService.boundedContext.question.repository;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
  Question findBySubject(String subject);

  Question findBySubjectAndContent(String subject, String content);

  List<Question> findBySubjectLike(String keyword);

  // 페이징 된 데이터를 반환
  Page<Question> findAll(Pageable pageable);

  @Modifying
  @Transactional
  @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
  void clearAutoIncrement();
}
