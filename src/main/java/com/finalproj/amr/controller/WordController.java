package com.finalproj.amr.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/word")
public class WordController {

    private Map<String, Word> cache_easy;
    private Map<String, Word> cache_medium;
    private Map<String, Word> cache_hard;
    public WordController() {
    }

    @PostConstruct
    public void init() throws URISyntaxException {
        word_availability_checker(); // call it once at startup
    }

    @GetMapping()
    public Word getWord(){
        return new Word("lol", "laughing out lout");
    }
    @Scheduled(fixedDelay = 1000)
    void word_availability_checker() throws URISyntaxException {
        if(cache_easy.size() >= 10 || cache_medium.size() >= 10 ||cache_hard.size() >= 10){
            return;
        }
        Random random = new Random();
        HttpRequest getEasyRequest = HttpRequest.newBuilder()
                .uri(new URI(""))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

    }

    private String getUrl(String mode){
        Random random = new Random();
        int length=5;
        if (mode.equals("MEDIUM")){
            length = 7;
        } else if (mode.equals("HARD")) {
            length = 9;
        }
        String baseurl = "https://random-words-api.kushcreates.com/api?language=en&category=";
        String centerurl = "&length=";
        String endurl = "&words=1";
        String category = "";
        int categ = random.nextInt(3);
        if(categ==1){
            category = "animals";
        } else if (categ==2) {
            category = "birds";
        } else if (categ==3) {
            category = "sports";
        }
        String fullurl = baseurl+category+centerurl+length+endurl;
        return fullurl;
    }


}

class Word{
    private String word;
    private String definition;

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
