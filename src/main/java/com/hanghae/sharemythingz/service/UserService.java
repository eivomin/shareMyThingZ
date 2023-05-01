package com.hanghae.sharemythingz.service;

import com.hanghae.sharemythingz.dto.LoginRequestDto;
import com.hanghae.sharemythingz.dto.SignupRequestDto;
import com.hanghae.sharemythingz.entity.User;
import com.hanghae.sharemythingz.entity.UserRoleEnum;
import com.hanghae.sharemythingz.jwt.JwtUtil;
import com.hanghae.sharemythingz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    /* 회원가입 */
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        System.out.println("username: "+username+"password : "+password);

        // encoding 하기 전 password 검사
        password = passwordEncoder.encode(password);

        // 회원 중복 확인
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new IllegalStateException("중복된 회원이 있습니다.");
        }

        String email = signupRequestDto.getEmail();

        String image_url = signupRequestDto.getImage_url();

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalStateException("관리자 토큰과 일치하지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username,  password,  email,  role,  image_url);
        userRepository.save(user);
    }

    //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
    private boolean isValidPassword(String password) {
        String pattern = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
        if (Pattern.matches(pattern, password)) return true;
        else return false;
    }

    /* 로그인 */
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        System.out.println("로그인 성공 username = "+username+" password: "+password);
        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("해당 이름으로 사용자가 존재하지 않습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalStateException("아이디와 비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}
