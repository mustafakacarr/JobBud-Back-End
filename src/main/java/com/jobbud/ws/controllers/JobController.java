package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.requests.JobRequest;
import com.jobbud.ws.services.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public JobEntity createJob(JobRequest jobRequest) {
        return jobService.addJob(jobRequest);
    }

    @GetMapping
    public List<JobEntity> getJobs(@RequestParam Optional<Long> ownerId) {
        return jobService.getJobs(ownerId);
    }

    @GetMapping("/{jobId}")
    public JobEntity getJobs(@PathVariable long jobId) {
        return jobService.getJobById(jobId);
    }

    // edit
    @PutMapping("/{jobId}")
    public JobEntity updateJob(@PathVariable long jobId, JobRequest jobRequest) {
        return jobService.updateJob(jobId, jobRequest);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> softDeleteJob(@PathVariable long jobId) {
        try {
            jobService.deleteJob(jobId);
            return new ResponseEntity("Job successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
