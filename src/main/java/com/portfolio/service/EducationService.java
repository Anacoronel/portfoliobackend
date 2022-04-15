package com.portfolio.service;

import com.portfolio.entity.Education;
import com.portfolio.repository.EducationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EducationService {
    
    @Autowired
    EducationRepository educationRepository;
    
    public List<Education> list() {
        return educationRepository.findAll();
    }
    
    public Optional<Education> get(int id) {
        return educationRepository.findById(id);
    }
    
    public void delete(int id) {
        educationRepository.deleteById(id);
    }
    
    public void save(Education education) {
        educationRepository.save(education);
    }
    
    public boolean existsById(int id) {
        return educationRepository.existsById(id);
    }
    
    public List<Education> listByUserId(int id) {
        return educationRepository.findByUserId(id);
    }
}
