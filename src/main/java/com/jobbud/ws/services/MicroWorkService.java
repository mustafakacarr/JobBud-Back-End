package com.jobbud.ws.services;

import com.jobbud.ws.entities.MicroWorkEntity;
import com.jobbud.ws.repositories.MicroTransactionRepository;
import com.jobbud.ws.repositories.MicroWorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroWorkService {
    private MicroWorkRepository microWorkRepository;
    public MicroWorkService(MicroWorkRepository microWorkRepository) {
        this.microWorkRepository = microWorkRepository;
    }


    public List<MicroWorkEntity> getMicroWorks() {
        return microWorkRepository.findAll();
    }
}
