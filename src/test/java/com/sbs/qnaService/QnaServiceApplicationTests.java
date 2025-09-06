package com.sbs.qnaService;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class QnaServiceApplicationTests {

  @Autowired
  private QuestionRepository questionRepository;

	@Test
  @DisplayName("데이터 저장")
	void t1() {
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);  // 첫번째 질문 저장

    Question q2 = Question.builder()
        .subject("스프링부트 모델 질문입니다.")
        .content("id는 자동으로 생성되나요?")
        .createDate(LocalDateTime.now())
        .build();

    questionRepository.save(q2);  // 두번째 질문 저장
	}

  @Test
  @DisplayName("findAll")
  void t2() {
    // SELECT * FROM question;
    List<Question> all = questionRepository.findAll();
    assertEquals(2, all.size());

    Question q = all.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }
}
