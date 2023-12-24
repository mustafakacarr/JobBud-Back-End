package com.jobbud.ws.controllers;

import com.jobbud.ws.requests.JobRequest;
import com.jobbud.ws.requests.JobUpdateRequest;
import com.jobbud.ws.responses.JobResponse;
import com.jobbud.ws.services.JobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/jobs")
@SecurityRequirement(name = "JobBud Auth with Jwt")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public JobResponse createJob(JobRequest jobRequest) {
        return jobService.addJob(jobRequest);
    }

    @GetMapping
    public List<JobResponse> getJobs(@RequestParam Optional<Long> ownerId) {
        return jobService.getJobs(ownerId);
    }

    @GetMapping("/{jobId}")
    public JobResponse getJobs(@PathVariable long jobId) {
        return jobService.getJobById(jobId);
    }

    // edit
    @PutMapping("/{jobId}")
    public JobResponse updateJob(@PathVariable long jobId, JobUpdateRequest jobRequest) {
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
