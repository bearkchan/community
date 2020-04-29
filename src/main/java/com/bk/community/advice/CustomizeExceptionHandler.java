package com.bk.community.advice;

import com.bk.community.dto.ResultDTO;
import com.bk.community.exception.CustomizeErrorCode;
import com.bk.community.exception.CustomizeException;
import com.mysql.cj.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : bear
 * @date : 2020-04-14 23:45
 **/

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(HttpServletRequest request, Throwable ex, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            if (ex instanceof CustomizeException) {
                return ResultDTO.errOf((CustomizeException) ex);
            } else {
                return ResultDTO.errOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }
}
