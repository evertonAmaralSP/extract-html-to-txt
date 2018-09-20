package br.com.fera.california;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
    private static final String URL_EXTRACT = "https://www.baka-tsuki.org/project/index.php?title=Tate_no_Yuusha_no_Nariagari:Web_Chapter_108_(Brazilian_Portuguese)";
    private static final String URL_EXTRACT2 = "https://www.baka-tsuki.org/project/index.php?title=Tate_no_Yuusha_no_Nariagari:Web_Chapter_%s_(Brazilian_Portuguese)";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner testExtracaoAndCreateRedShift() {
        return (args) -> {
            log.info("START");
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL_EXTRACT);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            // Read the contents of an entity and return it as a String.
            String content = EntityUtils.toString(entity);
//            System.out.println(content);
            Document doc = Jsoup.parse(content);

            Elements resultLinks = doc.select("#firstHeading");
            doc.select("#mw-content-text .mw-headline").get(0).text();
            doc.select("#mw-content-text > p");
            log.info("END");
        };
    }

}
