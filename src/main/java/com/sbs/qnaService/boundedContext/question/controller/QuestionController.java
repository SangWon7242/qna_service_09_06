package com.sbs.qnaService.boundedContext.question.controller;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("/list")
  public String showList(Model model) {
    // findAll() : 모든 데이터를 조회
    // SELECT * FROM question;
    List<Question> questionList = questionService.getList();
    model.addAttribute("questionList", questionList);

    return "question/question_list";
  }
}
