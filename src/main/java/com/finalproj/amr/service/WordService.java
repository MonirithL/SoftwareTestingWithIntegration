package com.finalproj.amr.service;


import com.finalproj.amr.entity.Word;
import com.finalproj.amr.jsonEntity.Dictionary;
import com.finalproj.amr.jsonEntity.RandomWord;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class WordService {
    public WordService() {
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
                        .uri(new URI(getUrl(1)))
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
