package com.finalproj.amr.jsonObject;

import com.finalproj.amr.Word;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Dictionary {
    private String word;
    private List<Meaning> meanings;

    public String getWord() {
        return word;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "word='" + word + '\'' +
                ", meanings=" + meanings.get(0) +
                '}';
    }
    public String getDefinition(){
        return meanings.getFirst().getDefinitions().get(0).getDefinition();
    }
    public String getPartOfSpeech(){
        return meanings.getFirst().getPartOfSpeech();
    }

    public static Dictionary getDictionary(String randWord){
        String encodedWord = URLEncoder.encode(randWord, StandardCharsets.UTF_8);
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try{
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.dictionaryapi.dev/api/v2/entries/en/"+encodedWord)).build();
            HttpResponse<String> getResponse =
                    httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (getResponse.statusCode() == 200) {
                String jsonBody = getResponse.body();
                Dictionary[] dict = gson.fromJson(jsonBody, Dictionary[].class);

                if (dict != null && dict.length > 0 && dict[0] != null) {
                        return dict[0];
                }
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

class Meaning {
    private String partOfSpeech;
    private List<Definition> definitions;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public String toString() {
        return "Meaning{" +
                "partOfSpeech='" + partOfSpeech + '\'' +
                ", definitions=" + definitions.get(0) +
                '}';
    }
}

class Definition {
    private String definition;  // the actual text

    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "definition='" + definition + '\'' +
                '}';
    }
}