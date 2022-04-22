package com.awbd.project.controller;


import com.awbd.project.model.CarType;
import com.awbd.project.model.Job;
import com.awbd.project.model.JobType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "h2")
class JobControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewJobAndGetById_success() throws Exception {
        Job job = new Job();
        job.setType(JobType.INTERIOR_AND_EXTERIOR);
        job.setCarType(CarType.MINIBUS);
        job.setPrice(15.0);
        job.setDurationMinutes(25L);
        job.setNumberOfEmployees(2);

        MvcResult mvcResult = mockMvc.perform(post("/jobs").flashAttr("job", job))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jobs"))
                .andReturn();

        String id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/jobs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("jobs"));
    }
}