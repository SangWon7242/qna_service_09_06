package com.sbs.qnaService.boundedContext.question.entity;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
public class Question {
  @Id // Primary Key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
  private Integer id; // Integer 는 null을 허용함

  @Column(length = 200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate; // Date Time

  // mappedBy = "question" -> Answer 필드에 있는 question와 매핑
  // CascadeType.REMOVE : 질문이 삭제되면 그 안에 달려있는 답변도 같이 삭제
  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  private List<Answer> answerList = new ArrayList<>();
}
