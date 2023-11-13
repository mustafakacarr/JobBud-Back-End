package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.requests.WorkRequest;
import com.jobbud.ws.services.WorkService;
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


}
