package com.m2t.hachikodictionary.client;

import com.m2t.hachikodictionary.dto.client.WordAudio;
import com.m2t.hachikodictionary.dto.client.WordOfTheDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public WordOfTheDay getWordOfTheDay() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        LocalDate currentDate = LocalDate.now();
        String date = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String url = wordnikUrl + "/word.json/wordOfTheDay?date=" + date + "&api_key={api_key}";
        Map<String, String> params = Collections.singletonMap("api_key", wordnikApiKey);
        try {
            Map<String, Object> wordOfTheDayMap = restTemplate.getForObject(url, Map.class, params);
            if (wordOfTheDayMap != null) {
                Integer wordOfTheDayId = (Integer) wordOfTheDayMap.get("id");
                String wordOfTheDayTitle = (String) wordOfTheDayMap.get("word");
                String wordOfTheDayDefinition = (String) wordOfTheDayMap.get("note");

                List<Map<String, Object>> definitions = (List<Map<String, Object>>) wordOfTheDayMap.get("definitions");
                String wordOfTheDayType = null;
                if (definitions != null && !definitions.isEmpty()) {
                    wordOfTheDayType = (String) definitions.get(0).get("partOfSpeech");
                }
                return new WordOfTheDay(wordOfTheDayId, wordOfTheDayTitle, wordOfTheDayDefinition, wordOfTheDayType);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error getting Word of the Day from Wordnik: " + e.getMessage(), e);
            return null;
        }
    }
}
