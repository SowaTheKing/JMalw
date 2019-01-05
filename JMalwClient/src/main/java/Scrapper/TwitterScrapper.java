package Scrapper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.Image;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        return webClient.getPage(BASE_URL+SEARCH_HASHTAG);
    }

    public void getImages(HtmlPage htmlPage) {
        List<HtmlElement> imagesList = (List<HtmlElement>) htmlPage.getByXPath("//div[@class='card-photo']");

        if (imagesList.isEmpty()) {
            System.out.println("Nothing found!");
        }

        List<String> DirtyLinks = new ArrayList<>();
        for (HtmlElement element : imagesList) {
            DirtyLinks.add(element.getFirstChild().getFirstChild().asXml());
        }
        System.out.println(DirtyLinks);
        List<String> cleanLinks = cleanUpDirtyLinks(DirtyLinks);
    }

    private List<String> cleanUpDirtyLinks(List<String> elements) {
        List<String> cleanLinks = new ArrayList<>();
        for (String dirtyLink : elements) {
            String s = StringUtils.substringBetween(dirtyLink, "\"");
            cleanLinks.add(s);
        }
        return cleanLinks;
    }

}
