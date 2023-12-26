package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.requests.WorkCreateRequest;
import com.jobbud.ws.requests.WorkUpdateRequest;
import com.jobbud.ws.requests.WorkUpdateStatusRequest;
import com.jobbud.ws.responses.WorkResponse;
import com.jobbud.ws.services.WorkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/works")
@SecurityRequirement(name = "JobBud Auth with Jwt")
public class WorkController {
    private WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    public List<WorkResponse> getWorks(@RequestParam Optional<Long> userId) {
        return workService.getWorks(userId);
    }

    @PostMapping
    public WorkEntity createWork(WorkCreateRequest workCreateRequest) {
        return workService.addWork(workCreateRequest);
    }

    @GetMapping("/{workId}")
    public WorkResponse getWorkById(@PathVariable long workId) {
        return workService.getWorkById(workId);
    }

    // the requests that came here indicates that the work is completed
    @PutMapping("/{workId}")
    public WorkResponse updateWork(@PathVariable long workId, WorkUpdateRequest workUpdateRequest) {
        return workService.updateWork(workId, workUpdateRequest);
    }

    //This request indicates that the work is approved or rejected by customer
    @PutMapping("/{workId}/status")
    public ResponseEntity<String> updateWorkStatus(@PathVariable long workId,@RequestBody WorkUpdateStatusRequest workUpdateStatusRequestRequest) {
        return workService.updateWorkStatus(workId, workUpdateStatusRequestRequest);
    }

    @DeleteMapping("/{workId}")
    public ResponseEntity<String> softDeleteWork(@PathVariable long workId) {
        try {
            workService.deleteWork(workId);
            return new ResponseEntity("Work successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
