package com.sbs.qnaService.boundedContext.question.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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
}
