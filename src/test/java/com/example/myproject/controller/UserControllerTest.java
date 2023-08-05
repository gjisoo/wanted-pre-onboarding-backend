package com.example.myproject.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testJoinEndpoint() throws Exception {
        String userJson = "{\"email\":\"test@example.com\",\"password\":\"password1234\"}";

        mockMvc.perform(post("/join")
            .contentType("application/json")
            .content(userJson))
            .andExpect(status().isOk())
            .andExpect(content().string("회원가입이 완료되었습니다."));
    }
}
