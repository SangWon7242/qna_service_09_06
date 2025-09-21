package com.sbs.qnaService.global.base.initData;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import com.sbs.qnaService.boundedContext.user.service.UserService;
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
  CommandLineRunner initData(QuestionService questionService, UserService userService) {
    return args -> {
      SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
      SiteUser user2 = userService.create("user2", "user2@test.com", "1234");

      Question q1 = questionService.create("질문1 제목입니다.", "질문1 내용입니다.", user1);
      Question q2 = questionService.create("질문2 제목입니다.", "질문2 내용입니다.", user2);
    };
  }
}
