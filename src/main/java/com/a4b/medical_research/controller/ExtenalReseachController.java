package com.a4b.medical_research.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a4b.medical_research.dto.ResearchPaperResponse;
import com.a4b.medical_research.model.ResearchPaper;
import com.a4b.medical_research.service.ExternalResearchService;

@RestController
@RequestMapping("/api/research/external")
public class ExtenalReseachController {
@Autowired
private ExternalResearchService service;


@GetMapping("/fetch")
public ResponseEntity<List<ResearchPaperResponse>> fetchall(
        @RequestParam String pmids) {

    String xml = service.fetchPubMedPapers(pmids);

    return ResponseEntity.ok(service.parsePapers(xml));
}
@GetMapping("/search")
public ResponseEntity<List<ResearchPaper>> search(
        @RequestParam String query) {

    return ResponseEntity.ok(service.saveandSearch(query));
}

}
