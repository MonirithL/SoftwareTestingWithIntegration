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
    private int CACHE_SIZE = 5;
    private int CACHE_MAX = 100;

    private WordService wordService;

    private Map<String, Word> cache_word;
    private final Random random = new Random(38917248);

    public Map<String, Word> getCache_word() {
        return cache_word;
    }

    public WordController(WordService wordService) {
        cache_word = new HashMap<>();
        this.wordService = wordService;

    }
    @PostConstruct
    public void init() {
        word_availability_checker();
    }

    @GetMapping()
    public Word getWord(){
        int num=random.nextInt(1,cache_word.size());
        Word selected = new ArrayList<>(cache_word.values()).get(num);
        cache_word.remove(selected.getWord());
        return selected;
    }

    @Scheduled(fixedDelay = 5000)
    void word_availability_checker() {
        int sanity = 0;
        int wordsAddedThisRun = 0; // track how many words added this run
        int maxWordsPerRun = 5;

        while (wordsAddedThisRun < maxWordsPerRun && sanity < 20) {
            Word word = wordService.generateWord();
            if (word == null || word.getWord() == null || word.getDefinition() == null) {
                sanity++;
                continue;
            }

            String key = word.getWord().toLowerCase();

            // skip duplicates in cache
            if (cache_word.containsKey(key)) {
                sanity++;
                continue;
            }

            // optional: skip if definition contains the word itself
            if (!word.getDefinition().toLowerCase().contains(key)) {
                cache_word.put(key, word);
                wordsAddedThisRun++;
                System.out.println("ADDED WORD: " + key + " | Cache size: " + cache_word.size());
            }

            sanity++;
        }

        System.out.println("Tries this run: " + sanity + " | Words added this run: " + wordsAddedThisRun);
    }
}


