package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.utils.Helper;
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
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Helper helper;

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testAdminOffersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("offers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("controllerAction"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }


    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testOfferDetailsGet() throws Exception {
        OfferEntity offerEntityById = helper.createOfferOne();
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/details/?id=" + offerEntityById.getId())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("offer-detail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("offer"));
    }
}
