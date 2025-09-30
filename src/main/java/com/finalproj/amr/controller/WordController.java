package com.finalproj.amr.controller;

import com.finalproj.amr.Word;
import com.finalproj.amr.jsonObject.RandomWord;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RestController
@RequestMapping("/api/word")
public class WordController {

    private Map<String, Word> cache_word;
    private final Random random = new Random(38917248);

    public WordController() {
        cache_word = new HashMap<>();
        word_availability_checker();
    }

    @GetMapping()
    public Word getWord(){
        int num=random.nextInt(1,cache_word.size());
        Word selected = new ArrayList<>(cache_word.values()).get(num);
        cache_word.remove(selected.getWord());
        return selected;
    }

    @GetMapping("/test")
    public List<Word> test(){
        return new ArrayList<>(cache_word.values());
    }

    @Scheduled(fixedDelay = 5000)
    void word_availability_checker() {

        while (cache_word.size() < 10){
            Word word = Word.generateWord();
            if(word!= null && !word.getDefinition().toLowerCase().contains(word.getWord().toLowerCase())){
                cache_word.put(word.getWord(),word);
                System.out.println("UPDATED ----------------------- CACHE WORDS CONTAINS "+cache_word.size()+" items");
            }
        }

    }
}


