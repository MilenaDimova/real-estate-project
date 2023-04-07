package bg.softuni.myrealestateproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("email", "test@example.com")
                .param("firstName", "Test")
                .param("lastName", "Testov")
                .param("phoneNumber", "0888994455")
                .param("password", "123")
                .param("confirmPassword", "123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    }

    @Test
    void testRegistrationGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }


    @Test
    void testRegistrationShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("email", "test@example.com")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("phoneNumber", "0888994455")
                        .param("password", "12")
                        .param("confirmPassword", "12"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("register"));

    }
}
