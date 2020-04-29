package com.bk.community.controller;

import com.bk.community.dto.CommentCreateDTO;
import com.bk.community.dto.ResultDTO;
import com.bk.community.exception.CustomizeErrorCode;
import com.bk.community.model.Comment;
import com.bk.community.model.User;
import com.bk.community.service.CommentService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author : bear
 * @date : 2020-04-26 00:06
 **/

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return ResultDTO.errOf(CustomizeErrorCode.NOT_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isEmptyOrWhitespaceOnly(commentCreateDTO.getContent())) {
            return ResultDTO.errOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);

        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
