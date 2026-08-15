package com.a4b.medical_research.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class ResearchPaper {
private Long id;
private String title;
private String abstractText;
private String authors;
private String journal;
private LocalDateTime publicationDate;
private String doi;
private String pmid;
private String sourceUrl;
}
