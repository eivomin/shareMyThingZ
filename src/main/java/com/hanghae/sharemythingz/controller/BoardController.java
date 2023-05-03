package com.hanghae.sharemythingz.controller;

import com.hanghae.sharemythingz.jwt.JwtUtil;
import com.hanghae.sharemythingz.repository.UserRepository;
import com.hanghae.sharemythingz.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/board")
    public ModelAndView board() {
        return new ModelAndView("index");
    }

    // 로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환
    @GetMapping("/user-info")
    @ResponseBody
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUsername();
    }

    // 게시판 반환
    @GetMapping("/board/{id}")
    @ResponseBody
    public ModelAndView moveBoard(@PathVariable Long id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("list");
        view.addObject("board_id", id);
        return view;
    }
}

