package com.sbs.qnaService;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.repository.AnswerRepository;
import com.sbs.qnaService.boundedContext.answer.service.AnswerService;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import com.sbs.qnaService.boundedContext.user.repository.UserRepository;
import com.sbs.qnaService.boundedContext.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class QnaServiceApplicationTests {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private QuestionService questionService;

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private AnswerService answerService;

  @BeforeEach // 테스트 케이스 실행 전에 한번 실행
  void beforeEach() {
    // 답변 데이터 삭제
    answerRepository.deleteAll();
    answerRepository.clearAutoIncrement();

    // 질문 데이터 삭제
    questionRepository.deleteAll();
    questionRepository.clearAutoIncrement();

    // 회원 데이터 삭제
    userRepository.deleteAll();;
    userRepository.clearAutoIncrement();

    // 회원 2명 생성
    SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
    SiteUser user2 = userService.create("user2", "user2@test.com", "1234");

    Question q1 = questionService.create("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.", user1);
    Question q2 = questionService.create("스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?", user2);

    // 답변 데이터 생성
    // 1번 질문에 대한 답변
    Answer a1 = answerService.create(q1, "네 자동으로 생성됩니다.", user2);
    q1.addAnswer(a1); // 질문과 답변을 한 로직에서 처리 가능

    // 2번 질문에 대한 답변
    Answer a2 = answerService.create(q2, "sbb는 스프링부트 프로젝트입니다.", user2);
    q2.addAnswer(a2);
  }

	@Test
  @DisplayName("데이터 저장")
	void t1() {
    SiteUser user1 = userService.getUser("user1");

    questionService.create("스프링부트 학습은 어떻게 해야 하나요?", "스프링부트 학습은 처음입니다.", user1);
    questionService.create("자바를 이용한 객체지향 설계는 어떻게 하나요?", "자바 객체지향 설계 방법을 모르겠습니다.", user1);
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
    Optional<Question> oq = questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    SiteUser user2 = userService.getUser("user2");
    Answer a = answerService.create(q, "네 자동으로 생성됩니다.", user2);

    assertEquals("네 자동으로 생성됩니다.", a.getContent());
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

  @Test
  @DisplayName("검색, 질문 제목으로 검색")
  void t12() {
    Page<Question> searchResult = questionService.getList(0, "sbb가 무엇인가요?");
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 질문 내용으로 검색")
  void t13() {
    Page<Question> searchResult = questionService.getList(0, "sbb에 대해서 알고 싶습니다.");
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 질문자 이름으로 검색")
  void t14() {
    Page<Question> searchResult = questionService.getList(0, "user1");
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 답변 내용으로 검색")
  void t15() {
    Page<Question> searchResult = questionService.getList(0, "네 자동으로 생성됩니다.");
    assertEquals(1, searchResult.getContent().get(0).getId());
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 답변자 이름으로 검색")
  void t16() {
    Page<Question> searchResult = questionService.getList(0, "user2");
    assertEquals(2, searchResult.getContent().get(0).getId());
    assertEquals(2, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("대량의 테스트 데이터 만들기")
  void t17() {
    SiteUser user2 = userService.getUser("user2");

    IntStream.rangeClosed(3, 300)
        .forEach(no ->
            questionService.create(
                "테스트 제목입니다. %d".formatted(no),
                "테스트 내용입니다%d".formatted(no), user2));
  }
}
