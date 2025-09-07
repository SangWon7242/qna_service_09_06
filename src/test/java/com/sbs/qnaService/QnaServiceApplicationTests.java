package com.sbs.qnaService;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.repository.AnswerRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class QnaServiceApplicationTests {

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @BeforeEach // 테스트 케이스 실행 전에 한번 실행
  void beforeEach() {
    // 질문 데이터 삭제
    answerRepository.deleteAll();
    answerRepository.clearAutoIncrement();

    // 질문 데이터 삭제
    questionRepository.deleteAll();
    questionRepository.clearAutoIncrement();
    
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);

    Question q2 = Question.builder()
        .subject("스프링부트 모델 질문입니다.")
        .content("id는 자동으로 생성되나요?")
        .createDate(LocalDateTime.now())
        .build();

    questionRepository.save(q2);

    // 답변 데이터 생성
    Answer a1 = new Answer();
    a1.setContent("네 자동으로 생성됩니다.");
    a1.setCreateDate(LocalDateTime.now());
    q2.addAnswer(a1); // 질문과 답변을 한 로직에서 처리 가능

    answerRepository.save(a1);
  }

	@Test
  @DisplayName("데이터 저장")
	void t1() {
    Question q1 = new Question();
    q1.setSubject("스프링부트 학습은 어떻게 해야 하나요?");
    q1.setContent("스프링부트 학습은 처음입니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);  // 첫번째 질문 저장

    Question q2 = Question.builder()
        .subject("자바를 이용한 객체지향 설계는 어떻게 하나요?")
        .content("자바 객체지향 설계 방법을 모르겠습니다.")
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

  @Test
  @DisplayName("findById")
  void t3() {
    // SELECT * FROM question WHERE id = 1;
    Optional<Question> oq = questionRepository.findById(1);
    if(oq.isPresent()) { // isPresent : 값이 존재하는지 확인
      Question q = oq.get();
      assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
  }

  @Test
  @DisplayName("findBySubject")
  void t4() {
    // SELECT * FROM question WHERE subject = 'sbb가 무엇인가요?';
    Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
    assertEquals(1, q.getId());
  }

  /*
  SELECT *
  FROM question
  WHERE subject = 'sbb가 무엇인가요?'
  AND content = 'sbb에 대해서 알고 싶습니다.';
  */
  @Test
  @DisplayName("findBySubjectAndContent")
  void t5() {
    Question q = questionRepository.findBySubjectAndContent(
        "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
    assertEquals(1, q.getId());
  }

  /*
  SELECT *
  FROM question
  WHERE subject LIKE 'sbb%';
  */
  @Test
  @DisplayName("findBySubjectLike")
  void t6() {
    List<Question> qList = questionRepository.findBySubjectLike("sbb%");
    Question q = qList.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  /*
  UPDATE question
  SET content=?,
  create_date=?,
  subject=?
  where id=?
  */
  @Test
  @DisplayName("데이터 수정하기")
  void t7() {
    // SELECT * FROM question WHERE id = 1;
    Optional<Question> oq = questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    q.setSubject("수정된 제목");
    questionRepository.save(q);
  }

  /*
  DELETE FROM question
  WHERE id=?
  */
  @Test
  @DisplayName("데이터 삭제하기")
  void t8() {
    // SELECT COUNT(*) FROM question;
    assertEquals(2, questionRepository.count());
    Optional<Question> oq = questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    questionRepository.delete(q);
    assertEquals(1, questionRepository.count());
  }

  // 질문 데이터 가져오기
  /*
  SELECT *
  FROM question AS q1
  WHERE q1.id = ?
  */

  // 특정 질문에 대한 답변 추가
  /*
  INSERT INTO answer
  SET content = ?,
  create_date = ?,
  question_id = ?;
  */

  @Test
  @DisplayName("답변 데이터 생성 후 저장")
  void t9() {
    // v1
    Optional<Question> oq = questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    // v2
    // Optional q = questionRepository.findById(2).get();

    Answer a = new Answer();
    a.setContent("네 자동으로 생성됩니다.");
    a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
    a.setCreateDate(LocalDateTime.now());

    /*
    Answer a2 = Answer.builder()
        .content("네 자동으로 생성됩니다.")
        .question(q)
        .createDate(LocalDateTime.now())
        .build();
     */

    answerRepository.save(a);
  }

  @Test
  @DisplayName("답변 데이터 조회")
  void t10() {
    Optional<Answer> oa = answerRepository.findById(1);
    assertTrue(oa.isPresent());
    Answer a = oa.get();
    // 답변 데이터의 question_id가 2번이냐?
    assertEquals(2, a.getQuestion().getId());
  }


  @Test
  @DisplayName("질문을 통해 답변 찾기")
  @Transactional // 테스트에서는 기본적으로 @Transactional 없이 DB 연결이 이어지지 않는다.
  @Rollback(false) // 테스트 메서드가 끝난 후에도 트랜잭션이 롤백되지 않고 커밋이 된다.
  void t11() {
    // SELECT * FROM question WHERE id = 2;
    Optional<Question> oq = questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get(); // get을 한 뒤에 DB 연결을 끊음

    // findById 메서드 실행 후 DB 연결이 끊어짐
    // @Transactional : 해당 메서드가 종료될 때까지 DB연결이 유지가 됨

    // SELECT * FROM answer WHERE question_id = 2;
    List<Answer> answerList = q.getAnswerList();

    assertEquals(1, answerList.size());
    assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
  }
}
