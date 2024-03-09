package com.m2t.hachikodictionary.client;

import com.m2t.hachikodictionary.dto.client.WordAudio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class WordnikClient {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WordnikClient.class);

    @Value("${WORDNIK_URL}")
    private String wordnikUrl;

    @Value("${WORDNIK_API_KEY}")
    private String wordnikApiKey;


    public WordnikClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WordAudio getWordAudio(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String url = wordnikUrl + "/word.json/" + title + "/audio?useCanonical=false&limit=1&api_key={api_key}";
        Map<String, String> params = Collections.singletonMap("api_key", wordnikApiKey);
        try {
            List wordAudioResponse = restTemplate.getForObject(url, List.class, params);
            if(wordAudioResponse != null && wordAudioResponse.get(0) != null) {
                Map<String, Object> wordAudioMap = (Map<String, Object>) wordAudioResponse.get(0);
                Integer wordAudioId = (Integer) wordAudioMap.get("id");
                String wordTitle = (String) wordAudioMap.get("word");
                Double duration = (Double) wordAudioMap.get("duration");
                String fileUrl = (String) wordAudioMap.get("fileUrl");
                WordAudio wordAudio = new WordAudio(wordAudioId, wordTitle, duration, fileUrl);
                return wordAudio;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error getting word audio from Wordnik: " + e.getMessage());
            return null;
        }

    }
}
