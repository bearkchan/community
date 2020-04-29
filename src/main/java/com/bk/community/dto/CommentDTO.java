package com.bk.community.dto;

import com.bk.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bear
 * @date : 2020-04-26 00:09
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer type;
    private User user;
}
