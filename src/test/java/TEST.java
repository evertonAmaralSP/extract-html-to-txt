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

public class TEST {
    private static final String URL_EXTRACT = "https://www.baka-tsuki.org/project/index.php?title=Tate_no_Yuusha_no_Nariagari:Web_Chapter_%d_(Brazilian_Portuguese)";
    private static final Map<Integer, String> volumes = new HashMap<>();
    private static final Set<String> set = new HashSet<>();

    static {
        volumes.put(1, "Eventos Cobertos no Volume 1");//1
        volumes.put(24, "Eventos Cobertos no Volume 2");//24
        volumes.put(47, "Eventos Cobertos no Volume 3");//47
        volumes.put(65, "Eventos Cobertos no Volume 4");//65
        volumes.put(79, "Arco de Cal Mira");//79
        volumes.put(115, "Arco da Tartaruga Espiritual");//115
        volumes.put(131, "Arco da Reconstrução Parte 1");//131
        volumes.put(155, "Arco da Reconstrução Parte 2");//155
        volumes.put(174, "Arco do Dragão da Ira");//174
        volumes.put(190, "Arco dos Heróis Caídos");//190
        volumes.put(224, "Arco da JUSTIÇA");//224
        volumes.put(256, "Arco do Contra-ataque da Waifu de Cauda Fofinha");//256
        volumes.put(275, "Arco d'Os Novos Sete Pecados");//275
        volumes.put(291, "Arco do Houou");//291
        volumes.put(308, "Arco das Sete Estrelas");//308
        volumes.put(334, "Arco da Frente Unida");//334
        volumes.put(353, "Arco da Rainha das Vadias");//353
        volumes.put(357, "Arco do Ark");//357
        volumes.put(365, "Arco para Queimar a Bruxa");//365
        volumes.put(375, "Arco do Epílogo");//375

        set.add("server.port");

        set.add("redis.queue.server.host");
        set.add("redis.queue.server.port");
        set.add("redis.pool.max.size");
        set.add("psl.retrive.tracking.code.range.enabled");
        set.add("psl.microservice.thread.delayed.reprocess");
        set.add("execute.queue.thread.count.per.type");
        set.add("execute.queue.thread.count.per.type.all");
        set.add("execute.queue.thread.count.per.typenum.volumes");
        set.add("execute.queue.thread.count.per.type.cut.time");
        set.add("execute.queue.thread.count.per.lp");
        set.add("execute.queue.thread.count.per.lp.correios");
        set.add("execute.queue.thread.count.per.lp.loggi");
        set.add("execute.queue.thread.count.per.lp.loggi_express");
        set.add("execute.queue.thread.count.per.lp.jadlog");
        set.add("execute.queue.thread.count.per.lp.notfis");
        set.add("execute.queue.thread.count.per.lp.total");
        set.add("execute.queue.thread.count.per.lp.intelipost");
        set.add("psl.max.retry.count");
        set.add("max.orders.per.psl");
        set.add("psl.microservice.thread.pool.jobs.size");
        set.add("extra.sysnode.shipment.order.connect.timeout");
        set.add("extra.sysnode.shipment.order.socket.timeout");
        set.add("aws.s3.access.key");
        set.add("aws.s3.secret.key");
        set.add("local.path.files");
        set.add("aws.s3.files.number.of.seconds.lock");
        set.add("aws.s3.files.bucket.name");
        set.add("aws.s3.assets.bucket.name");
        set.add("postgres.driver");
        set.add("postgres.url");
        set.add("postgres.user");
        set.add("postgres.passwd");
        set.add("readonly.postgres.url");
        set.add("readonly.postgres.user");
        set.add("readonly.postgres.passwd");
        set.add("health.checker.enabled");
        set.add("health.checker.connect.timeout");
        set.add("health.checker.socket.timeout");
        set.add("health.checker.sleep");
        set.add("psl.additional.service.ar.code");
        set.add("cache.serializer");
        set.add("redis.server.hosts.ports");
        set.add("redis.server.pool.idle");
        set.add("redis.server.pool.max");
        set.add("psl.processing.timeout.in.minutes");
        set.add("psl.microservice.processing.expiration.in.minutes");
        set.add("loggi.async.attempt");
        set.add("total.tracking-url");
        set.add("psl.max_number_days_before_to_send");
        set.add("proceda.tracking.socket.timeout.in.millis");
        set.add("proceda.tracking.data.timeout.in.millis");
        set.add("proceda.tracking.ftp.passive.mode");
        set.add("esprinter.auth-user");
        set.add("esprinter.auth-passwd");
        set.add("esprinter.host");
        set.add("esprinter.port");
        set.add("esprinter.protocol");
        set.add("cache.admin-user");
        set.add("cache.client");
        set.add("cache.property");
        set.add("cache.logistic-provider");
        set.add("cache.delivery-method");
        set.add("cache.zip-code-information");

    }

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
