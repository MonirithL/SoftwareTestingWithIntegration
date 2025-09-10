package com.finalproj.amr.jsonObject;

import java.util.Objects;

public class RandomWord {
    private String word;
    private String category;

    public RandomWord(String word, String category) {
        this.word = word;
        this.category = category;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RandomWord{" +
                "word='" + word + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public boolean checkCateg(){
        if(word==null ||category==null){
            return false;
        }
        if(Objects.equals(category, "animals") || Objects.equals(category, "birds") || Objects.equals(category, "sports")){
            return true;
        }
        return false;
    }


}
