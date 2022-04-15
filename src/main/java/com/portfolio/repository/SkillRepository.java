package com.portfolio.repository;

import com.portfolio.entity.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByUserId(int Userid);
}
