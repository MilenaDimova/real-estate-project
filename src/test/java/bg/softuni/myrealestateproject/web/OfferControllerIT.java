package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.enums.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Helper helper;

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testAddOfferGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-offer"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("offerAddBindingModel"));
    }

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testAddOfferPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("statusType", StatusTypeEnum.PENDING.name())
                        .param("city", CityNameEnum.SOFIA.name())
                        .param("area", "Borovo")
                        .param("offerType", OfferTypeEnum.RENT.name())
                        .param("estateType", EstateTypeEnum.HOUSE.name())
                        .param("propertyType", PropertyTypeEnum.KITCHENETTE.name())
                        .param("floor", String.valueOf(1))
                        .param("room", String.valueOf(1))
                        .param("bed", String.valueOf(1))
                        .param("bath", String.valueOf(1))
                        .param("quadrature", String.valueOf(1))
                        .param("price", BigDecimal.ONE.toString())
                        .param("activeFrom", LocalDate.now().plusDays(1).toString())
                        .param("description", "Hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-offer-message"));
    }

    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testAddOfferShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("statusType", StatusTypeEnum.PENDING.name())
                        .param("city", (String) null)
                        .param("area", "Borovo")
                        .param("offerType", OfferTypeEnum.RENT.name())
                        .param("estateType", EstateTypeEnum.HOUSE.name())
                        .param("propertyType", PropertyTypeEnum.KITCHENETTE.name())
                        .param("floor", String.valueOf(1))
                        .param("room", String.valueOf(1))
                        .param("bed", String.valueOf(1))
                        .param("bath", String.valueOf(1))
                        .param("quadrature", String.valueOf(1))
                        .param("price", BigDecimal.ONE.toString())
                        .param("activeFrom", LocalDate.now().plusDays(1).toString())
                        .param("description", "Hello"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("add"));
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


    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testSaleOffersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/sales")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("offers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("controllerAction"))
                .andExpect(MockMvcResultMatchers.view().name("offers"));
    }


    @Test
    @WithUserDetails(
            value = "admin@example.com",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void testRentOffersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/rents")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("offers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("controllerAction"))
                .andExpect(MockMvcResultMatchers.view().name("offers"));
    }
}
