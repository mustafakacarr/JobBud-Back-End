package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.requests.WorkRequest;
import com.jobbud.ws.services.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/works")
public class WorkController {
    private WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    public List<WorkEntity> getWorks(@RequestParam Optional<Long> userId){
        return workService.getWorks(userId);
}

    @PostMapping
    public WorkEntity createWork(WorkRequest workRequest){
        return workService.addWork(workRequest);
    }

    @GetMapping("/{workId}")
    public WorkEntity getWorkById(@PathVariable long workId) {
        return workService.getWorkById(workId);
    }

    // edit work
    @PutMapping("/{workId}")
    public WorkEntity updateWork(@PathVariable long workId, WorkRequest workRequest) {
        return workService.updateWork(workId, workRequest);
    }
    @DeleteMapping("/{workId}")
    public ResponseEntity<String> softDeleteWork(@PathVariable long workId) {
        WorkEntity deletedWork = workService.softDeleteWork(workId);
        if (deletedWork != null) {
            return ResponseEntity.ok("Work soft deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Work not found");
        }
    }
}
