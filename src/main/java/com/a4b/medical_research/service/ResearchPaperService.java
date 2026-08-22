package com.a4b.medical_research.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a4b.medical_research.dto.QueryResponse;
import com.a4b.medical_research.dto.ResearchPaperResponse;
import com.a4b.medical_research.model.ResearchPaper;
import com.a4b.medical_research.repo.ResearchPaperRepo;

@Service
public class ResearchPaperService {
@Autowired
private ResearchPaperRepo researchPaperrepo;

    
public String createResearchPaper(ResearchPaper request){   
    if(researchPaperrepo.existsByPmid(request.getPmid())){    
         throw new RuntimeException("Research Paper already exists");
    }
   researchPaperrepo.save(request);
   return "Successfully Completed";
}
public List<ResearchPaper> getAllResearchPaper(){
    List<ResearchPaper> papers=researchPaperrepo.findAll();
    return papers;
}
public List<QueryResponse> getResponse(String keyword) {
    List<ResearchPaper> papers=researchPaperrepo.findByKeywordsContainingIgnoreCase(keyword);
    List<QueryResponse> reposes=new ArrayList<>();
    for(ResearchPaper paper:papers){
      QueryResponse q= QueryResponse.builder().abstractText(paper.getAbstractText()).id(paper.getId()).title(paper.getTitle()).authers(paper.getAuthors()).build();
             reposes.add(q);

    }
    return reposes;
}
  public ResearchPaper saveExternalPapers(ResearchPaperResponse response){
      return  researchPaperrepo.findByPmid(response.getSourceId())
                                .orElseGet(()-> {
                                    ResearchPaper paper=new ResearchPaper();
                                    paper=ResearchPaper.builder().abstractText(response.getAbstractText())
                                                                  .authors(String.join(",",response.getAuthors()))
                                                                  .pmid(response.getSourceId())
                                                                  .sourceUrl(response.getSourceUrl())
                                                                  .title(response.getTitle())
                                                                  .publicationDate(response.getPublicationDate())
                                                                   .build();
                                                return researchPaperrepo.save(paper);
                                });
    }

}
