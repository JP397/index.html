package edu.famu.cop3060.resources.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllResources_noFilters_returnsAllResources() throws Exception {
        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(8))));
    }

    @Test
    void getResourceById_existingId_returnsResource() throws Exception {
        mockMvc.perform(get("/api/resources/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("FAMU Tutorial Center"))
                .andExpect(jsonPath("$.category").value("Tutoring"));
    }

    @Test
    void getResourceById_nonExistingId_returns404() throws Exception {
        mockMvc.perform(get("/api/resources/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllResources_withCategoryFilter_returnsFilteredResources() throws Exception {
        mockMvc.perform(get("/api/resources")
                        .param("category", "Tutoring"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].category", everyItem(is("Tutoring"))));
    }

    @Test
    void getAllResources_withQueryFilter_returnsMatchingResources() throws Exception {
        mockMvc.perform(get("/api/resources")
                        .param("q", "math"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void getAllResources_withBothFilters_returnsMatchingResources() throws Exception {
        mockMvc.perform(get("/api/resources")
                        .param("category", "Tutoring")
                        .param("q", "writing"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Writing Center"));
    }
}
