package com.awbd.project.repository;

import com.awbd.project.model.CarType;
import com.awbd.project.model.Job;
import com.awbd.project.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    boolean existsByTypeAndCarType(JobType type, CarType carType);
}
