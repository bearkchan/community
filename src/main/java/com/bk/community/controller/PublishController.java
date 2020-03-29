package com.bk.community.controller;

import com.bk.community.model.Question;
import com.bk.community.model.User;
import com.bk.community.service.PublishService;
import com.bk.community.service.UserService;
import com.mysql.cj.util.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author bear
 */
@Controller
public class PublishController {

    @Autowired
    PublishService publishService;
    @Autowired
    UserService userService;

    @GetMapping("/publish")
    public String publish(Model model) {
        // 为了页面数据回显
        model.addAttribute("question",new Question());
        return "publish";
    }

    @PostMapping("/publish")
    public String create(Question question, HttpServletRequest request, Model model) {
        String errMsg;
        User user=null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userService.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null) {
            errMsg = "用户尚未登陆";
        } else if (StringUtils.isNullOrEmpty(question.getTitle())) {
            errMsg = "标题不能为空";
        } else if (StringUtils.isNullOrEmpty(question.getDescription())) {
            errMsg = "补充不能为空";
        } else if (StringUtils.isNullOrEmpty(question.getTag())) {
            errMsg = "标签不能为空";
        } else {

            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCreator(user.getId());
            publishService.create(question);
            return "redirect:/";
        }
        model.addAttribute("question", question);
        model.addAttribute("errMsg", errMsg);
        return "publish";
    }
}
