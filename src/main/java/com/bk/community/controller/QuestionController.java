package com.bk.community.controller;

import com.bk.community.dto.CommentCreateDTO;
import com.bk.community.dto.CommentDTO;
import com.bk.community.dto.QuestionDTO;
import com.bk.community.service.CommentService;
import com.bk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author bear
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    /**
     * 根据问题的id获取详情
     * @param id 问题id
     * @return template
     */
    @GetMapping("/question/{id}")
    public String detail(@PathVariable(name = "id") Long id,
                         Model model){
        Integer isUpdated = questionService.incView(id);
        if (isUpdated==1){
            QuestionDTO questionDTO = questionService.detail(id);
            model.addAttribute("question",questionDTO);
        }

        List<CommentDTO> comments=commentService.listByQuestionId(id);
        model.addAttribute("comments",comments);
        return "question";
    }
}
