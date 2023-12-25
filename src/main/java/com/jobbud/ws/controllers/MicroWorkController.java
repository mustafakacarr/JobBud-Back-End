package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.MicroWorkEntity;
import com.jobbud.ws.services.MicroWorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/microworks")
public class MicroWorkController {
    private MicroWorkService microWorkService;

    public MicroWorkController(MicroWorkService microWorkService) {
        this.microWorkService = microWorkService;
    }

    @GetMapping
    public List<MicroWorkEntity> getMicroWorks() {
        return microWorkService.getMicroWorks();
    }
}
