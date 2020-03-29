package com.bk.community.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bear
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUserDTO {
    private String name;
    private Long id;
    private String bio;
    @JSONField(name = "avatar_url")
    private String avatarUrl;
}
