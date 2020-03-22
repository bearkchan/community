package com.bk.community.dto;

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
}
