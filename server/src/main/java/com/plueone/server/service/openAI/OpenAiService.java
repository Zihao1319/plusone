package com.plueone.server.service.openAI;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.plueone.server.models.Interest;
import com.plueone.server.models.Subinterest;
import com.plueone.server.service.InfoRetrievalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class OpenAiService {

    @Autowired
    private InfoRetrievalService infoRtrvSvc;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    @Value("${openai.api.key}")
    private String OPENAI_API_KEY;

    public Optional<String> generateResponses(String userId, String prompt) throws Exception {

        Optional<List<Interest>> opt = infoRtrvSvc.getUserInterest(userId);
        Optional<List<Subinterest>> optSub = infoRtrvSvc.getUserSubInterest(userId);

        String interests = "";
        String subInterests = "";

        String chatGPTPrompt = "";

        if (opt.isPresent()) {
            List<Interest> list = opt.get();

            List<String> array = new ArrayList<>();

            for (Interest i : list) {
                array.add(i.getInterestName());
            }

            interests = String.join(",", array);
        }

        if (optSub.isPresent()) {
            List<Subinterest> list = optSub.get();

            List<String> array = new ArrayList<>();

            for (Subinterest i : list) {
                array.add(i.getSubInterestName());
            }

            subInterests = String.join(",", array);
        }

        if (!interests.isEmpty()) {
            chatGPTPrompt = "My potential date has the following interests:" + interests;
        } 

        if (!subInterests.isEmpty()){
            chatGPTPrompt += " and subinterests such as:" + subInterests + ". ";
        }

        chatGPTPrompt += prompt;

        chatGPTPrompt += " in one single line.";

        System.out.printf(">>>>>>%s\n", chatGPTPrompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", chatGPTPrompt);
        requestBody.put("max_tokens", 256);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        String payload = "";
        int statusCode = 500;

        try {
            resp = template.exchange(OPENAI_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            System.out.printf(">>>>>>full res: %s\n", resp);

            payload = resp.getBody();
            System.out.printf(">>>>>>%s\n", payload);
            statusCode = resp.getStatusCode().value();

        } catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
            statusCode = ex.getStatusCode().value();

            return Optional.empty();

        }

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject resJson = reader.readObject();

        JsonArray arr = resJson.getJsonArray("choices");
        JsonObject data = arr.getJsonObject(0);
        String text = data.getString("text");
        System.out.printf(">>>>>>%s\n", text);

        return Optional.of(text);

    }
}
