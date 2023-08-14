package com.example.demo.User.LogIn.controller;
//
//import com.example.demo.User.LogIn.DTO.JwtResponseDTO;
//import com.example.demo.User.LogIn.DTO.LoginDTO;
//import com.example.demo.User.LogIn.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @PostMapping
//    public JwtResponseDTO login(@RequestBody LoginDTO loginDTO) {
//        JwtResponseDTO response = loginService.login(loginDTO);
//
//        if (response != null) {
//            return response;
//        } else {
//            // 로그인 실패 처리 로직
//            return null;
//        }
//    }
//}

import com.example.demo.User.LogIn.dto.LoginRequest;
import com.example.demo.User.LogIn.model.LoginUser;
import com.example.demo.User.LogIn.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    private void errorManager(String value, String errorMessage) {
        if (value.isEmpty()) { throw new IllegalArgumentException(errorMessage); }
    }
    @PostMapping
    public String logIn(@RequestBody LoginRequest dto) throws Exception {
        errorManager(dto.getUsername(), "아이디를 입력해주세요.");
        errorManager(dto.getPassword(), "비밀번호를 입력해주세요.");

        LoginUser loginUser = loginService.findByUserName(dto.getUsername());

        if (loginUser == null || !encoder.matches(dto.getPassword(), loginUser.getPassword())) {
            errorManager("", "아이디 또는 비밀번호를 확인 해주세요.");
        }
        return loginService.login(dto.getUsername(), dto.getPassword());
    }
}