package com.sbs.qnaService.boundedContext.answer.controller;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.service.AnswerService;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
  private final AnswerService answerService;
  private final QuestionService questionService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
    // 관련 질문을 얻어옴
    Question question = questionService.getQuestion(id);

    // TODO: 답변을 저장한다.
    Answer answer = answerService.create(question, content);

    return "redirect:/question/detail/%s".formatted(id);
  }
}
