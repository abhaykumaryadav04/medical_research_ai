package com.a4b.medical_research.external;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.a4b.medical_research.dto.ResearchPaperResponse;

@Component
public class PubMedXmlParser {

   public List<ResearchPaperResponse> parse(String xml) {

    List<ResearchPaperResponse> papers = new ArrayList<>();

    try {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        // PubMed XML contains DOCTYPE
        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                false
        );

        // Security: disable external entities
        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false
        );

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false
        );

        factory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false
        );

        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(
                new InputSource(new StringReader(xml))
        );

        NodeList articles =
                document.getElementsByTagName("PubmedArticle");

        for (int i = 0; i < articles.getLength(); i++) {

            Element article =
                    (Element) articles.item(i);

            ResearchPaperResponse paper =
                    parseArticle(article);

            papers.add(paper);
        }

        return papers;

    } catch (Exception e) {

        throw new RuntimeException(
                "Failed to parse PubMed XML", e
        );
    }
}
    

    private ResearchPaperResponse parseArticle(Element article) {
        String pmid = getText(article, "PMID");
        String title = getText(article, "ArticleTitle");
        String publicationDate = getPublicationDate(article);
        String abstractText = getAbstract(article);
        List<String> authors = getAuthors(article);

        String sourceUrl ="https://pubmed.ncbi.nlm.nih.gov/" + pmid + "/";

        return new ResearchPaperResponse(
                pmid,
                title,
                authors,
                abstractText,
                publicationDate,
                sourceUrl
        );
    }

    private String getText(Element parent, String tagName) {
        NodeList nodes =parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes.item(0)
                .getTextContent()
                .trim();
    }

    private List<String> getAuthors(Element article) {
        List<String> authors = new ArrayList<>();
        NodeList authorNodes = article.getElementsByTagName("Author");

        for (int i = 0; i < authorNodes.getLength(); i++) {
            Element author = (Element) authorNodes.item(i);
            String lastName= getText(author, "LastName");
            String initials= getText(author, "Initials");
            if (lastName != null) {
                if (initials != null) {
                    authors.add(lastName + " " + initials);
                } else {
                    authors.add(lastName);
                }
            }
        }
        return authors;
    }

    private String getAbstract(Element article) {

        NodeList nodes =
                article.getElementsByTagName("AbstractText");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nodes.getLength(); i++) {

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(
                    nodes.item(i)
                            .getTextContent()
                            .trim()
            );
        }

        return result.isEmpty()
                ? null
                : result.toString();
    }

    private String getPublicationDate(Element article) {

        String year = getText(article, "Year");
        String month = getText(article, "Month");
        String day = getText(article, "Day");
        if (year == null) {
            return null;
        }
        StringBuilder date =new StringBuilder(year);
        if (month != null) {
            date.append("-").append(month);
        }
        if (day != null) {
            date.append("-").append(day);
        }
        return date.toString();
    }
}