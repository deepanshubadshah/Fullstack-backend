package com.deepanshu.FullstackApp.service;

import com.deepanshu.FullstackApp.model.Foundation;
import com.deepanshu.FullstackApp.repo.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoundationService {

    @Autowired
    private FoundationRepository foundationRepository;

    public Foundation updateFoundation(Long id, Foundation updatedFoundation) {
        Optional<Foundation> existingFoundation = foundationRepository.findById(id);
        if (existingFoundation.isPresent()) {
            Foundation changedFoundation = existingFoundation.get();
            changedFoundation.setName(updatedFoundation.getName());

            return foundationRepository.save(changedFoundation);
        }
        return null;
    }
}
