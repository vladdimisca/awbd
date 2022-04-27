package com.awbd.project.controller;


import com.awbd.project.model.CarType;
import com.awbd.project.model.Job;
import com.awbd.project.model.JobType;
import com.awbd.project.service.impl.JobServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class JobControllerTest {

    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobServiceImpl jobService;

    @Test
    @DisplayName("Create job - success")
    void create_success() throws Exception {
        Job job = getJob();
        Job savedJob = getSavedJob();

        when(jobService.create(job)).thenReturn(savedJob);

        mockMvc.perform(post("/jobs").flashAttr("job", job))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jobs"));
    }

    @Test
    @DisplayName("Update job - success")
    void update_success() throws Exception {
        Job updatedJob = getSavedJob();

        when(jobService.update(ID, updatedJob)).thenReturn(updatedJob);

        mockMvc.perform(put("/jobs/{id}", ID).flashAttr("job", updatedJob))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jobs"));
    }

    @Test
    @DisplayName("Get job by id - success")
    void getById_success() throws Exception {
        Job job = getSavedJob();

        when(jobService.getById(ID)).thenReturn(job);

        mockMvc.perform(get("/jobs/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("job-info"));
    }

    @Test
    @DisplayName("Get all jobs - success")
    void getAll_success() throws Exception {
        when(jobService.getAll()).thenReturn(List.of(getSavedJob()));

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("jobs"));
    }

    @Test
    @DisplayName("Delete job by id - success")
    void delete_success() throws Exception {
        mockMvc.perform(delete("/jobs/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jobs"));
    }

    @Test
    @DisplayName("Get job form - success")
    void getForm_success() throws Exception {
        mockMvc.perform(get("/jobs/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("job-form"));
    }

    @Test
    @DisplayName("Get update job form - success")
    void getUpdateForm_success() throws Exception {
        when(jobService.getById(ID)).thenReturn(getSavedJob());

        mockMvc.perform(get("/jobs/form/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("update-job-form"));
    }

    private Job getJob() {
        Job job = new Job();
        job.setType(JobType.EXTERIOR);
        job.setCarType(CarType.MINIBUS);
        job.setPrice(15.0);
        job.setNumberOfEmployees(5);
        job.setDurationMinutes(15L);

        return job;
    }

    private Job getSavedJob() {
        Job savedJob = getJob();
        savedJob.setId(ID);

        return savedJob;
    }
}