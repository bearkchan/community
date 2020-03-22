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
public class AccessTokenDTO {
    @JSONField(name = "client_id")
    private String clientId;
    @JSONField(name = "client_secret")
    private String clientSecret;
    private String code;
    @JSONField(name = "redirect_uri")
    private String redirectUri;
    private String state;
}
