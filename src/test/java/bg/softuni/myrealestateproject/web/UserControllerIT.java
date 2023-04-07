package bg.softuni.myrealestateproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testUserProfileGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userProfile"))
                .andExpect(MockMvcResultMatchers.view().name("/user/info"));
    }

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testUserUpdateGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/update")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("updateProfileBindingModel"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("emailExists"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("phoneNumberExists"))
                .andExpect(MockMvcResultMatchers.view().name("/user/update"));
    }

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testUserUpdatePut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/profile/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", "1")
                        .param("firstName", "Admin")
                        .param("lastName", "Adminov")
                        .param("email", "admin@example.com")
                        .param("phoneNumber", "0887123456")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testUserOffersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/offers")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userOffers"))
                .andExpect(MockMvcResultMatchers.view().name("user/offers"));
    }
}
