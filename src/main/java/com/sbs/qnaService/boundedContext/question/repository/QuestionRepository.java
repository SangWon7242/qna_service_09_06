package com.sbs.qnaService.boundedContext.question.repository;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
