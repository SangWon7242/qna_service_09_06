package com.sbs.qnaService.boundedContext.question.entity;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
@ToString
public class Question {
  @Id // Primary Key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
  private Integer id; // Integer 는 null을 허용함

  @Column(length = 200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate; // Date Time
  private LocalDateTime modifyDate;

  @ManyToOne
  private SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;

  // mappedBy = "question" -> Answer 필드에 있는 question와 매핑
  // CascadeType.REMOVE : 질문이 삭제되면 그 안에 달려있는 답변도 같이 삭제
  // fetch = FetchType.EAGER : 즉시 로딩을 통해 질문과 함께 답변도 같이 조회가 가능
  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  @Builder.Default // 빌더 패턴으로 객체를 생성할 때 필드 초기화를 할 수 있도록 함
  private List<Answer> answerList = new ArrayList<>();

  public void addAnswer(Answer answer) {
    answer.setQuestion(this); // Question 객체에 Answer 객체를 추가
    answerList.add(answer); // answerList에 답변 데이터를 추가
  }
}
