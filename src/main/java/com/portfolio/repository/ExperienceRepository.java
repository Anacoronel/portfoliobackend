package com.portfolio.repository;

import com.portfolio.entity.Experience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findByUserId(int Userid);
}
