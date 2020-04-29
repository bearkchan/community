package com.bk.community.controller;

import com.bk.community.dto.QuestionDTO;
import com.bk.community.model.Question;
import com.bk.community.model.User;
import com.bk.community.service.QuestionService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author bear
 */
@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    /**
     * 用于本人编辑问题页面跳转
     *
     * @param id    问题id
     * @param model 数据模型
     * @return 页面模板
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model,
                       HttpSession session) {
        QuestionDTO question = questionService.detail(id);
        // 判断当前session的id和当前id是否是同一个人，如果不是就跳转到首页
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getId().equals(question.getCreator())) {
            return "redirect:/";
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model) {
        // 为了页面数据回显
        model.addAttribute("question", new Question());
        return "publish";
    }

    @PostMapping("/publish")
    public String create(Question question, HttpServletRequest request, Model model) {
        String errMsg;

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            errMsg = "用户尚未登陆";
        } else if (StringUtils.isNullOrEmpty(question.getTitle())) {
            errMsg = "标题不能为空";
        } else if (StringUtils.isNullOrEmpty(question.getDescription())) {
            errMsg = "补充不能为空";
        } else if (StringUtils.isNullOrEmpty(question.getTag())) {
            errMsg = "标签不能为空";
        } else if (!checkQuestionId(question.getId(), user)) {
            errMsg = "非法操作";
        } else {
            question.setId(question.getId());
            question.setCreator(user.getId());
            questionService.createOrUpdate(question);
            return "redirect:/";
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("errMsg", errMsg);
        return "publish";
    }

    /**
     * 检查问题id是否是本人操作
     *
     * @param id   问题id
     * @param user 当前session用户
     * @return 是否是本人操作
     */
    public boolean checkQuestionId(Long id, User user) {
        if (id != null) {
            // 检查这个question的id所对应的question是否是对应session的用户
            QuestionDTO detail = questionService.detail(id);
            return detail != null && detail.getCreator().equals(user.getId());
        }
        return true;
    }
}
