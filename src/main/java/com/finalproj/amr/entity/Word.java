package com.finalproj.amr.entity;

import com.finalproj.amr.jsonEntity.Dictionary;
import com.finalproj.amr.jsonEntity.RandomWord;

public class Word{
    private String word;
    private String category;
    private String definition;
    private String partOfSpeech;

    public Word(Dictionary dict, RandomWord randomWord) {
        this.word = dict.getWord();
        this.category = randomWord.getCategory();
        this.definition = dict.getDefinition();
        this.partOfSpeech = dict.getPartOfSpeech();
    }

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

    public String getDefinition() {
        return definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", category='" + category + '\'' +
                ", definition='" + definition + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }



}