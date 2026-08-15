package com.a4b.medical_research.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a4b.medical_research.model.ResearchPaper;

@Repository
public interface ResearchPaperRepo extends JpaRepository<ResearchPaper,Long> {
boolean existsByPmid(String pmid);
}
