package com.sbs.qnaService.boundedContext.question.controller;

import com.sbs.qnaService.boundedContext.answer.input.AnswerForm;
import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.input.QuestionForm;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm) {
    return "question/question_form";
  }

  @PostMapping("/create")
  public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
    // 에러 메시지를 가지고 있니?
    // 가지고 있으면 true, 가지고 있지 않으면 false
    if (bindingResult.hasErrors()) {
      // 에러 메시지가 존재하면 question_form.html로 이동
      return "question/question_form";
    }

    questionService.create(questionForm.getSubject(), questionForm.getContent());

    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }

  @GetMapping("/list")
  public String showList(Model model, @RequestParam(defaultValue="0") int page) {
    Page<Question> paging = questionService.getList(page);
    model.addAttribute("paging", paging);

    return "question/question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);

    return "question/question_detail";
  }
}
