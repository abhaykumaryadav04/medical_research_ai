package com.a4b.medical_research.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.a4b.medical_research.model.ResearchPaper;

@Repository
public interface ResearchPaperRepo extends JpaRepository<ResearchPaper,Long> {
boolean existsByPmid(String pmid);
@Query("""
    SELECT r FROM ResearchPaper r
    WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(r.abstractText) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(r.authors) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
List<ResearchPaper> findByKeywordsContainingIgnoreCase(String keyword);
Optional<ResearchPaper> findByPmid(String pmid);
}
