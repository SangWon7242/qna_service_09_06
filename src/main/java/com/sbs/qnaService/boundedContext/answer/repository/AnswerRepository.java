package com.sbs.qnaService.boundedContext.answer.repository;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
