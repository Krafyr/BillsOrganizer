package com.bills.backend;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bills.backend.auth.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
//@Transactional
@AutoConfigureMockMvc
public class BillsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthenticationService authService;

    ObjectMapper objectMapper = new ObjectMapper();


    void setup() throws Exception {
//        Map<String,Object> body = new HashMap<>();
//        body.put("name","rick");
//        body.put("email","rick@gmail.com");
//        body.put("password","123456");
//
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(body))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }

    @Test
    public void shouldAccept() throws Exception {
//        String token = "a";
//
//        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate").para)
//                .andReturn()
//                .getResponse();

        Map<String,Object> body = new HashMap<>();
        body.put("username","rick");
        body.put("email","rick@gmail.com");
        body.put("password","123456");


        mvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}


