package com.deepanshu.FullstackApp.service;


import com.deepanshu.FullstackApp.model.Nonprofit;
import com.deepanshu.FullstackApp.repo.NonprofitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NonprofitService {

    @Autowired
    private NonprofitRepository nonprofitRepository;

    public Nonprofit updateNonprofit(Long id, Nonprofit updatedNonprofit) {
        Optional<Nonprofit> existingNonprofit = nonprofitRepository.findById(id);
        if (existingNonprofit.isPresent()){
            Nonprofit changedNonprofit = existingNonprofit.get();
            changedNonprofit.setName(updatedNonprofit.getName());
            changedNonprofit.setAddress(updatedNonprofit.getAddress());
            return nonprofitRepository.save(changedNonprofit);
        }
        return null;
    }
}
