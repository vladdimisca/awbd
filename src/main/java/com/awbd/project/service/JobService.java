package com.awbd.project.service;

import com.awbd.project.model.Job;

import java.util.List;

public interface JobService {
    Job create(Job job);

    Job update(Long id, Job job);

    Job getById(Long id);

    List<Job> getAll();

    void deleteById(Long id);
}
