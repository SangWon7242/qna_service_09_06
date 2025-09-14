package com.sbs.qnaService.boundedContext.answer.controller;

import com.sbs.qnaService.boundedContext.answer.entity.Answer;
import com.sbs.qnaService.boundedContext.answer.input.AnswerForm;
import com.sbs.qnaService.boundedContext.answer.service.AnswerService;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
  private final AnswerService answerService;
  private final QuestionService questionService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model,
                             @PathVariable("id") Integer id,
                             @Valid AnswerForm answerForm, BindingResult bindingResult) {
    // 관련 질문을 얻어옴
    Question question = questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question/question_detail";
    }

    // TODO: 답변을 저장한다.
    Answer answer = answerService.create(question, answerForm.getContent());

    return "redirect:/question/detail/%s".formatted(id);
  }
}
