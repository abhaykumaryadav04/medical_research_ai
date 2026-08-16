package com.a4b.medical_research.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a4b.medical_research.model.ResearchPaper;
import com.a4b.medical_research.service.ResearchPaperService;

@RestController
@RequestMapping("/api/research-papers")
public class ResearchPaperController {
@Autowired
private ResearchPaperService researchPaperService;
@PostMapping("/create")
public ResponseEntity<String> createResearchPaper(@RequestBody ResearchPaper paper){
    return ResponseEntity.ok(researchPaperService.createResearchPaper(paper));
}
@GetMapping("/getAll")
public ResponseEntity<List<ResearchPaper>> getAll(){
    return ResponseEntity.ok((researchPaperService.getAllResearchPaper()));
}


}
