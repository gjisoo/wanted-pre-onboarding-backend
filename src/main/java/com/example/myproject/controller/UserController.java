package com.example.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.entity.User;
import com.example.myproject.security.JwtUtil;
import com.example.myproject.service.UserService;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User joinRequest){

        if(!isValidEmail(joinRequest.getEmail())){
            return ResponseEntity.badRequest().body("이메일이 올바른 형식이 아닙니다. (@ 미포함)");
        }

        if(!isValidPassword(joinRequest.getPassword())){
            return ResponseEntity.badRequest().body("비밀번호는 8자이상이어야 합니다.");
        }

        joinRequest.setPassword(encryptPassword(joinRequest.getPassword()));
        userService.join(joinRequest);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest){
        if(!isValidEmail(loginRequest.getEmail()) || !isValidPassword(loginRequest.getPassword())){
            return ResponseEntity.badRequest().body("가입되지 않은 이메일이거나 비밀번호가 일치하지 않습니다.");
        }

        User user = userService.getUserByEmail(loginRequest.getEmail());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("가입되지 않은 이메일이거나 비밀번호가 일치하지 않습니다.");
        }
        
        String token = jwtUtil.generateToken(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).body("로그인이 완료되었습니다.");
    }

    private boolean isValidEmail(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    private boolean isValidPassword(String password){
        if(password.trim().length() >= 8){
            return true;
        }
        return false;
    }

    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }
}
