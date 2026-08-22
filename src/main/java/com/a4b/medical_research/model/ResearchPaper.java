package com.a4b.medical_research.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchPaper {
 @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(columnDefinition = "TEXT")
private String title;
@Column(columnDefinition = "TEXT")
private String abstractText;
@Column(columnDefinition = "TEXT")
private String authors;
private String journal;
private String publicationDate;
@Column(unique = true)
private String pmid;
private String sourceUrl;
}
