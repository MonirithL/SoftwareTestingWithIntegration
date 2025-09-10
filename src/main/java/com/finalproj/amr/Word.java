package com.finalproj.amr;

import com.finalproj.amr.jsonObject.Dictionary;
import com.finalproj.amr.jsonObject.RandomWord;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

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

    public static String getUrl(int wordCount){
        Random random = new Random();
        int length= random.nextInt(5,13);
        String baseurl = "https://random-words-api.kushcreates.com/api?language=en&category=";
        String centerurl = "&length=";
        String endurl = "&words="+wordCount;
        String category = "";
        int categ = random.nextInt(1,4);
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

    public static Word generateWord() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            try {
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(new URI(Word.getUrl(1)))
                        .build();

                HttpResponse<String> getResponse =
                        httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

                if (getResponse.statusCode() == 200) {
                    String jsonBody = getResponse.body();
                    RandomWord[] randWord = gson.fromJson(jsonBody, RandomWord[].class);

                    if (randWord != null && randWord.length > 0 && randWord[0] != null) {
                        if(randWord[0].checkCateg()){
                            Dictionary newDict = Dictionary.getDictionary(randWord[0].getWord());
                            if(newDict!=null){
                                return new Word(newDict, randWord[0]);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Request failed, retrying... " + e.getMessage());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting to retry", ie);
            }
        }
    }

}