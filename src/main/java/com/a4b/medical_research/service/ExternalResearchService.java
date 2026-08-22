package com.a4b.medical_research.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.a4b.medical_research.dto.ResearchPaperResponse;
import com.a4b.medical_research.external.PubMedXmlParser;
import com.a4b.medical_research.model.ResearchPaper;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class ExternalResearchService {

private final RestClient restClient;
private final PubMedXmlParser xmlParser;
@Autowired
private ResearchPaperService researchPaperService;
public ExternalResearchService(PubMedXmlParser xmlParser){
    this.xmlParser=xmlParser;
    this.restClient = RestClient.builder()
                .baseUrl("https://eutils.ncbi.nlm.nih.gov/entrez/eutils")
                .build();
  
}
public String searchPubMed(String query) {

    return restClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/esearch.fcgi")
                    .queryParam("db", "pubmed")
                    .queryParam("term", query)
                    .queryParam("retmode", "json")
                    .queryParam("retmax", 5)
                    .build())
            .retrieve()
            .body(String.class);
}
public String fetchPubMedPapers(String pmids) {

    return restClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/efetch.fcgi")
                    .queryParam("db", "pubmed")
                    .queryParam("id", pmids)
                    .queryParam("retmode", "xml")
                    .build())
            .retrieve()
            .body(String.class);
}
public List<ResearchPaperResponse> parsePapers(String xml) {
    return xmlParser.parse(xml);
}
   public List<ResearchPaperResponse> searchResearchPapers(
            String query) {
        String searchResponse = searchPubMed(query);
        List<String> pmids = extractPmids(searchResponse);
        if (pmids.isEmpty()) {
            return List.of();
        }
        String ids = String.join(",", pmids);
        String xml = fetchPubMedPapers(ids);
        return xmlParser.parse(xml);
    }
       private List<String> extractPmids(String response) {
   try {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode idList =
                root.path("esearchresult")
                    .path("idlist");
        List<String> pmids = new java.util.ArrayList<>();
        for (JsonNode id : idList) {
            pmids.add(id.asText());
        }
        return pmids;
    } catch (Exception e) {
        throw new RuntimeException(  "Failed to parse PubMed search response",e);
    }
    }
    public List<ResearchPaper> saveandSearch(String query){
        List<ResearchPaperResponse> responses= searchResearchPapers(query);
        List<ResearchPaper> papers=new ArrayList<>();
        for(ResearchPaperResponse r:responses){
            ResearchPaper paper=researchPaperService.saveExternalPapers(r);
            papers.add(paper);
        }
     return papers;
    }
  
}
