package com.bk.community.controller;

import com.bk.community.dto.PaginationDTO;
import com.bk.community.dto.QuestionDTO;
import com.bk.community.model.User;
import com.bk.community.service.QuestionService;
import com.bk.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bear
 * @description community
 * @date 2020/3/30 4:20 下午
 */
@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    private enum ProfileSection {
        /**
         * 我的问题
         */
        QUESTIONS("questions", "我的问题"),
        /**
         * 我的回复
         */
        REPLIES("replies", "我的回复");

        private String section;
        private String sectionName;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        ProfileSection(String section, String sectionName) {
            this.section = section;
            this.sectionName = sectionName;
        }
    }

    @GetMapping(value = {"/profile/{section}", "/profile/"})
    public String profile(@PathVariable(name = "section", required = false) String section,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                          Model model
    ) {

        // 判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }


        if (StringUtils.isEmpty(section)) {
            section = "questions";
        }

        for (ProfileSection profileSection : ProfileSection.values()) {
            if (profileSection.getSection().equals(section)) {
                model.addAttribute("section", profileSection.getSection());
                model.addAttribute("sectionName", profileSection.getSectionName());
            }
        }
        if (ProfileSection.QUESTIONS.getSection().equals(section)) {
            PaginationDTO<QuestionDTO> pagination = questionService.getListByUser(user.getId(), page, size);
            model.addAttribute("pagination", pagination);
        }


        return "profile";
    }
}
