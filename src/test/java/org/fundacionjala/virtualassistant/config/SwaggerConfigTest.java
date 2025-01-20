package org.fundacionjala.virtualassistant.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class SwaggerConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOkStatusForSwaggerUIEndpoint() throws Exception {
        mockMvc.perform(get("/swagger-ui/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundStatusForSwaggerUIEndpoint() throws Exception {
        mockMvc.perform(get("/swagger-u/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkStatusAndJSONContentTypeForSwaggerResourcesEndpoint() throws Exception {
        mockMvc.perform(get("/swagger-resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("default"));
    }

    @Test
    void shouldReturnOkStatusForSwaggerAPIDocsEndpoint() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }
}
