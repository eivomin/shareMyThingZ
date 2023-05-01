package com.hanghae.sharemythingz.controller;

import com.hanghae.sharemythingz.dto.LoginRequestDto;
import com.hanghae.sharemythingz.dto.SignupRequestDto;
import com.hanghae.sharemythingz.dto.StatusResponseDto;
import com.hanghae.sharemythingz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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

}
