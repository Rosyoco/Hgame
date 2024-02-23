package com.hgame.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hgame.pojo.User;
import com.hgame.pojo.UserVO;
import com.hgame.properties.JwtProperties;
import com.hgame.result.Result;
import com.hgame.service.UserService;
import com.hgame.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private UserService userService;

	/**
     * 登录
     *
     * @param userVO Result<UserVO>
     * @return
     */
    @PostMapping("/login")
    public Result<UserVO> login(HttpServletRequest request,@RequestBody User user) {
        log.info("员工登录：{}", user);


        User userC = userService.loginService(user);

        if(userC == null) {
        	return Result.error("账号或密码错误,请重新登录");
        }
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getPassword());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserVO userVO = UserVO.builder()
        		.username(userC.getUsername())
        		.password(userC.getPassword())
        		.token(token)
        		.build();

        request.getSession().setAttribute("username", user.getUsername() +"21");
        return Result.success(userVO);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
    	log.info("用户退出登录：{}", request.getSession().getAttribute("username"));
    	request.getSession().removeAttribute("username");
		return Result.success("退出成功");
		}
}

