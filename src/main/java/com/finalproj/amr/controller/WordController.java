package com.finalproj.amr.controller;

import com.finalproj.amr.entity.Word;
import com.finalproj.amr.service.WordService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/word")
public class WordController {

    private WordService wordService;

    private Map<String, Word> cache_word;
    private final Random random = new Random(38917248);


    public WordController(WordService wordService) {
        cache_word = new HashMap<>();
        this.wordService = wordService;

    }
    @PostConstruct
    public void init() {
        word_availability_checker();
    }

    //return a word from cache
    @GetMapping()
    public Word getWord(){
        int num=random.nextInt(1,cache_word.size());
        Word selected = new ArrayList<>(cache_word.values()).get(num);
        cache_word.remove(selected.getWord());
        return selected;
    }

    //cron job for caching the word
    @Scheduled(fixedDelay = 5000)
    void word_availability_checker() {
        int sanity = 0;
        while (cache_word.size() < 5 && sanity<1000){
            Word word = wordService.generateWord();
            if (word == null || word.getWord() == null || word.getDefinition() == null) {
                sanity++;
                continue;
            }

            if (!word.getDefinition().toLowerCase().contains(word.getWord().toLowerCase())) {
                cache_word.put(word.getWord(), word);
                System.out.println("UPDATED ----------------------- CACHE WORDS CONTAINS " + cache_word.size() + " items");
            }
            sanity++;
        }
        System.out.println("Tries of "+ sanity+ " times!");
    }
}


