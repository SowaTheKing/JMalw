package Scrapper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterScrapper {

    private static final String SEARCH_HASHTAG = "Polityka";
    public static final String BASE_URL = "https://twitter.com/search?q=";
    private static WebClient webClient;

    public TwitterScrapper() {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    public HtmlPage getPage() throws IOException {
        return webClient.getPage(BASE_URL + SEARCH_HASHTAG);
    }

    public List<String> getImages(HtmlPage htmlPage) {
        List<HtmlElement> imagesList = (List<HtmlElement>) htmlPage.getByXPath("//div[@class='card-photo']");

        if (imagesList.isEmpty()) {
            System.out.println("Nothing found!");
        }

        List<String> dirtyLinks = new ArrayList<>();
        for (HtmlElement element : imagesList) {
            dirtyLinks.add(element.getFirstChild().getFirstChild().asXml());
        }

        return cleanUpDirtyLinks(dirtyLinks);
    }

    private List<String> cleanUpDirtyLinks(List<String> elements) {
        return elements.stream().map(item ->
                StringUtils.substringBetween(item, "\""))
                .collect(Collectors.toList());
    }

}
