package org.g9project4.main.services;

import lombok.RequiredArgsConstructor;
import org.g9project4.main.entities.VisitorCount;
import org.g9project4.main.repositories.VisitorCountRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorCountRepository visitorCountRepository;

    public void recordVisit(){
        LocalDate today = LocalDate.now();
        Optional<VisitorCount> optionalCount = visitorCountRepository.findByVisitDate(today);

        if(optionalCount.isPresent()){
            VisitorCount visitorCount = optionalCount.get();
            visitorCount.setVisitCount(visitorCount.getVisitCount() + 1);
            visitorCountRepository.save(visitorCount);
        } else {
            VisitorCount visitorCount = new VisitorCount();
            visitorCount.setVisitDate(today);
            visitorCount.setVisitCount(1);
            visitorCountRepository.save(visitorCount);
        }
    }
    public List<VisitorCount> getVisitorStatistics(){
        return visitorCountRepository.findAllByOrderByVisitDateAsc();
    }
}
