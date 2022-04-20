package com.awbd.project.controller;

import com.awbd.project.model.Job;
import com.awbd.project.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Job job, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "job-form";
        }

        jobService.create(job);
        return "redirect:/jobs";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Job job, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-job-form";
        }

        jobService.update(id, job);
        return "redirect:/jobs";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("jobs", jobService.getAll());
        return "jobs";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("job", jobService.getById(id));
        return "job-info";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        jobService.deleteById(id);
        return "redirect:/jobs";
    }

    @GetMapping("/form")
    public String jobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    @GetMapping("/form/{id}")
    public String updateJobForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("job", jobService.getById(id));
        return "update-job-form";
    }
}
