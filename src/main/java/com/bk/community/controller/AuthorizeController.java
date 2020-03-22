package com.bk.community.controller;

import com.bk.community.dto.AccessTokenDTO;
import com.bk.community.dto.GitHubUserDTO;
import com.bk.community.model.User;
import com.bk.community.provider.GitHubProvider;
import com.bk.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    public AuthorizeController() {
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpSession httpSession){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirectUri(redirectUri);
        accessTokenDTO.setClientId(clientID);
        accessTokenDTO.setClientSecret(clientSecret);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUserDTO gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.insertUser(user);
            httpSession.setAttribute("user",gitHubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
