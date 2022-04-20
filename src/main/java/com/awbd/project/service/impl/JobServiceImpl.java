package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ConflictException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Job;
import com.awbd.project.repository.JobRepository;
import com.awbd.project.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job create(Job job) {
        checkJobNotExisting(job);
        return jobRepository.save(job);
    }

    @Override
    public Job update(Long id, Job job) {
        Job existingJob = getById(id);
        if (!existingJob.getType().equals(job.getType()) || !existingJob.getCarType().equals(job.getCarType())) {
            checkJobNotExisting(job);
        }

        copyValues(existingJob, job);

        return jobRepository.save(existingJob);
    }

    @Override
    public Job getById(Long id) {
        return jobRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "job", id));
    }

    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Job job = getById(id);
        jobRepository.delete(job);
    }

    private void checkJobNotExisting(Job job) {
        if (jobRepository.existsByTypeAndCarType(job.getType(), job.getCarType())) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "Job");
        }
    }

    private void copyValues(Job to, Job from) {
        to.setType(from.getType());
        to.setDurationMinutes(from.getDurationMinutes());
        to.setCarType(from.getCarType());
        to.setPrice(from.getPrice());
        to.setNumberOfEmployees(from.getNumberOfEmployees());
    }
}
