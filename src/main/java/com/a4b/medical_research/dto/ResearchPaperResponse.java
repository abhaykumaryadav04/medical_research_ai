package com.a4b.medical_research.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResearchPaperResponse {
 private String sourceId;
    private String title;
    private List<String> authors;
    private String abstractText;
    private String  publicationDate;
    private String sourceUrl;

}
