package com.curso.JWTAuthenticationRest;


import com.curso.JWTAuthenticationRest.model.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    ObjectMapper om=new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

//    @Test
//    public void addUserTest() throws Exception {
//        Login new_user=new Login("gaurav", "1234");
//        System.out.println(new_user);
//        String jsonRequest=om.writeValueAsString(new_user);
//        System.out.println(jsonRequest);
//        MvcResult result=mockMvc.perform(post("/user/register").content(jsonRequest).
//                content(MediaType.APPLICATION_JSON_VALUE)).
//                andExpect(status().isOk()).andReturn();
//        boolean resultContent= Boolean.parseBoolean(result.getResponse().getContentAsString());
//        Assert.assertTrue(resultContent== Boolean.TRUE);
//    }
}
