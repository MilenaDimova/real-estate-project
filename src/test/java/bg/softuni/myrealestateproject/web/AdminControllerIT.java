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
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testAdminUsersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("controllerAction"))
                .andExpect(MockMvcResultMatchers.view().name("admin/users"));
    }
}
