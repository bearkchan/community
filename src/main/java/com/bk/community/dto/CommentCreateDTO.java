package com.bk.community.dto;

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
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
