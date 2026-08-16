package com.a4b.medical_research.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class ResearchPaper {
 @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
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
