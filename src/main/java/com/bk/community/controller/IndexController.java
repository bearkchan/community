package com.bk.community.controller;

import com.bk.community.dto.PaginationDTO;
import com.bk.community.dto.QuestionDTO;
import com.bk.community.model.Question;
import com.bk.community.model.User;
import com.bk.community.service.QuestionService;
import com.bk.community.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {


        PaginationDTO<QuestionDTO> pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
