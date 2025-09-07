package com.sbs.qnaService.global.base.initData;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile({"dev", "test"})
// NotProd : 실제 서비스를 제공하는 환경이 아니다.
// dev : 개발 모드
// test : 테스트 모드
public class NotProd {
  @Bean
  CommandLineRunner initData(QuestionRepository questionRepository) {
    return args -> {
      Question q1 = Question.builder()
          .subject("질문1 제목입니다.")
          .content("질문1 내용입니다.")
          .createDate(LocalDateTime.now())
          .build();

      questionRepository.save(q1);

      Question q2 = Question.builder()
          .subject("질문2 제목입니다.")
          .content("질문2 내용입니다.")
          .createDate(LocalDateTime.now())
          .build();

      questionRepository.save(q2);

    };
  }
}
