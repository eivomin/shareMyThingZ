package com.hanghae.sharemythingz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.sharemythingz.dto.LoginRequestDto;
import com.hanghae.sharemythingz.dto.SignupRequestDto;
import com.hanghae.sharemythingz.jwt.JwtUtil;
import com.hanghae.sharemythingz.service.KakaoService;
import com.hanghae.sharemythingz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    /* 회원가입 api */
    @PostMapping("/signup")
    public String signup(SignupRequestDto signUpRequestDto){
        userService.signup(signUpRequestDto);
        return "redirect:/api/board";
    }

    /* 로그인 api */
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }

    @GetMapping("/forbidden")
    public ModelAndView getForbidden() {
        return new ModelAndView("forbidden");
    }

    @PostMapping("/forbidden")
    public ModelAndView postForbidden() {
        return new ModelAndView("forbidden");
    }

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/api/board";
    }
}
