package com.bk.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bear
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /**
     *   `id` int(11) NOT NULL AUTO_INCREMENT,
     *   `title` varchar(50) DEFAULT NULL,
     *   `description` text,
     *   `gmt_create` bigint(20) DEFAULT NULL,
     *   `gmt_modified` bigint(20) DEFAULT NULL,
     *   `creator` int(11) DEFAULT NULL,
     *   `comment_count` int(11) DEFAULT '0',
     *   `view_count` int(11) DEFAULT '0',
     *   `like_count` int(11) DEFAULT '0',
     *   `tag` varchar(255) DEFAULT NULL,
     */
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
