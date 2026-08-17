package com.a4b.medical_research.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryResponse {
private String title;
private String abstractText;
private String authers;
private Long id;
}
