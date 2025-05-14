package de.starwit;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    static Properties config = new Properties();
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        loadProperties();

        String url = config.getProperty("broker.url");
        String user = config.getProperty("broker.username");
        String pw = config.getProperty("broker.password");
        String queueName = config.getProperty("client.target");
        
        listQueues(url, user, pw);
        sendMessage(url, user, pw, queueName);
    }

    private static void listQueues(String url, String user, String pw) {
        String getUrl = url + "search/org.apache.activemq:type=Broker,brokerName=single,destinationType=Queue,destinationName=*";

        // Make POST request to Jolokia
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(getUrl);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Origin", "http://localhost");
        get.setHeader("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((user+":"+pw).getBytes())); 
        
        CloseableHttpResponse response;
        try {
            response = client.execute(get);
            InputStream content = response.getEntity().getContent();

            Map<?, ?> result = mapper.readValue(content, Map.class);
            log.info("Jolokia response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    
            client.close();
        } catch (IOException e) {
            log.error("IO error " + e.getMessage());
        }        
    }

    private static void sendMessage(String url, String user, String pw, String queueName) {
        String mbean = String.format(
                "org.apache.activemq:type=Broker,brokerName=single,destinationType=Queue,destinationName=%s",
                queueName);

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "exec");
        payload.put("mbean", mbean);
        payload.put("operation", "sendTextMessage(java.lang.String)");
        payload.put("arguments", new Object[] { "Test" });

        String json;
        try {
            json = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("Can't create JSON payload " + e.getMessage());
            payload.forEach((k,v) -> log.error(k + ":" + v));
            return;
        }

        // Make POST request to Jolokia
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Origin", "http://localhost");
        post.setHeader("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((user+":"+pw).getBytes()));
        try {
            post.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e) {
            log.error("Can't encode json payload " + e.getMessage());
            return;
        }

        CloseableHttpResponse response;
        try {
            response = client.execute(post);
            InputStream content = response.getEntity().getContent();

            Map<?, ?> result = mapper.readValue(content, Map.class);
            log.info("Jolokia response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    
            client.close();
        } catch (IOException e) {
            log.error("IO error " + e.getMessage());
        }
    }

    private static void loadProperties() {
        try (InputStream in = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                config.load(in);
            } else {
                log.error("Can't find property file");
                System.exit(1);
            }

        } catch (IOException e) {
            log.error("Can't load property file, exiting " + e.getMessage());
            System.exit(1); // exit with error status
        }
    }
}
