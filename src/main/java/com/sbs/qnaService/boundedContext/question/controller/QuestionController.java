package com.sbs.qnaService.boundedContext.question.controller;

import com.sbs.qnaService.boundedContext.question.entity.Question;
import com.sbs.qnaService.boundedContext.question.repository.QuestionRepository;
import com.sbs.qnaService.boundedContext.question.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("/create")
  public String questionCreate() {
    return "question/question_form";
  }

  @PostMapping("/create")
  public String questionCreate(String subject, String content) {
    // TODO 질문을 저장한다.
    questionService.create(subject, content);
    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }

  @GetMapping("/list")
  public String showList(Model model) {
    // findAll() : 모든 데이터를 조회
    // SELECT * FROM question;
    List<Question> questionList = questionService.getList();
    model.addAttribute("questionList", questionList);

    return "question/question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);

    return "question/question_detail";
  }
}
