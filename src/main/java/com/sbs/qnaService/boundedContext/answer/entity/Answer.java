package com.sbs.qnaService.boundedContext.answer.entity;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;

  // 하나의 질문의 답변이 여러개일 수 있다.
  @ManyToOne // 좌측은 많고 우측은 하나다.
  @ToString.Exclude // ToString 대상에서 제외
  private Question question;

  @ManyToOne
  private SiteUser author;
}
