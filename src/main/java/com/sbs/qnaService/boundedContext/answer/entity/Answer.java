package com.sbs.qnaService.boundedContext.answer.entity;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  // 하나의 질문의 답변이 여러개일 수 있다.
  @ManyToOne // 좌측은 많고 우측은 하나다.
  private Question question;
}
