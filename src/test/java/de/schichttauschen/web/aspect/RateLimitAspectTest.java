package de.schichttauschen.web.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class RateLimitAspectTest extends AbstractTestNGSpringContextTests {

    private static final int RATE_LIMIT_CALL_COUNT = 5;
    @Autowired
    private MockMvc mockMvc;

    @Test(priority = 1)
    void testLimitOK() throws Exception {
        for (int i = 0; i < RATE_LIMIT_CALL_COUNT; i++) {
            this.mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/public/account/login")
                            .param("login", "test")
                            .param("password", "test"))
                    .andExpect(status().isOk());
        }
    }

    @Test(priority = 2)
    void testLimitNotOK() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/public/account/login")
                        .param("login", "test")
                        .param("password", "test"))
                .andExpect(status().is(429));
    }
}
