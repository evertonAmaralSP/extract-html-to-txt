import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestServer {
    private static final String URL_EXTRACT = "https://www.baka-tsuki.org/project/index.php?title=Tate_no_Yuusha_no_Nariagari:Web_Chapter_%d_(Brazilian_Portuguese)";
    private static final Map<Integer, String> volumes = new HashMap<>();
    private static final Set<String> set = new HashSet<>();

    //1 - 379
    @Test
    public void test() throws IOException {
        File arquivo2 = new File("/home/fera/workspace/california/tate-no-yuusha-no-nariagari.md");
        arquivo2.createNewFile();
        FileWriter fileW = new FileWriter(arquivo2);
        BufferedWriter buffW = new BufferedWriter(fileW);

        IntStream.range(1, 379).forEach(chapter -> {
            try {
                final String tituloVolume = volumes.get(chapter);
                if (Objects.nonNull(tituloVolume)) {
                    buffW.write(tituloVolume);
                    buffW.newLine();
                    buffW.write("===========");
                    buffW.newLine();
                    buffW.newLine();
                }
                String content = getBodyPage(chapter);
                Document doc = Jsoup.parse(content);

                final String titulo = extractTitle(doc);
                buffW.write(titulo);
                buffW.write("---------------");
                buffW.newLine();
                buffW.newLine();
                final Elements selects = extractElementP(doc);
                selects.stream().forEach(element -> {
                    try {
                        buffW.write(extractTextP(element));
                        buffW.newLine();
                        buffW.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                buffW.newLine();
                buffW.write("- - - - ");
                buffW.newLine();
                buffW.newLine();
            } catch (IOException e) {
            }

        });
        buffW.close();
        fileW.close();
    }

    private String extractTextP(Element element) {
        return element.text();
    }

    private Elements extractElementP(Document doc) {
        return doc.select("#mw-content-text > p");
    }

    private String extractTitle(Document doc) {
        return doc.select("#mw-content-text .mw-headline").get(0).text();
    }

    private String getBodyPage(int chapter) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(String.format(URL_EXTRACT, chapter));
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity);
    }

    @Test
    public void test2() {
        System.out.println(String.format(URL_EXTRACT, 1));
    }

    @Test
    public void test3() throws IOException {
        File arquivo2 = new File("/home/fera/workspace/california/tate-no-yuusha-no-nariagari.md");
        arquivo2.createNewFile();
        FileWriter fileW = new FileWriter(arquivo2);
        BufferedWriter buffW = new BufferedWriter(fileW);

        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials("it-ip", "po@905%");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();
            HttpGet request = new HttpGet("https://api.intelipost.com.br/sysnode/properties");

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            String content = EntityUtils.toString(entity);
            Document doc = Jsoup.parse(content);

//            final String titulo = extractTitle(doc);
            final Elements selects = doc.select("td");
            for (int i = 1; i <= selects.size(); ) {
                final String key = selects.get(i).text();
                final String valor = selects.get(i+1).text();
                i = i + 6;
                if(set.contains(key)) {
                    System.out.println(key +"="+ valor);
                }
            }

            //
        } catch (IOException e) {
        }

        buffW.close();
        fileW.close();
    }

}
