package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthFlowTest {

  @Autowired
  MockMvc mvc;

  @Test
  void unauthenticatedUserIsRedirectedToLogin() throws Exception {
    mvc.perform(get("/"))
      .andExpect(status().is3xxRedirection())
      .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  void publicPageIsAccessibleWithoutLogin() throws Exception {
    mvc.perform(get("/public"))
      .andExpect(status().isOk())
      .andExpect(view().name("public"));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void authenticatedUserCanSeeProtectedHomePage() throws Exception {
    mvc.perform(get("/"))
      .andExpect(status().isOk())
      .andExpect(view().name("index"));
  }
}
