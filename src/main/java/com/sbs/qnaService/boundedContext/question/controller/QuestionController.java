package com.sbs.qnaService.boundedContext.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {
  @GetMapping("/list")
  public String showList() {
    return "question/question_list";
  }
}
