package com.finalproj.amr.jsonEntity;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Dictionary2 {
    private String word;
    private Phonetic2[] phonetics;
    private Meaning2[] meanings;

    public String getWord() { return word; }
    public void setWord(String value) { this.word = value; }

    public Phonetic2[] getPhonetics() { return phonetics; }
    public void setPhonetics(Phonetic2[] value) { this.phonetics = value; }

    public Meaning2[] getMeanings() { return meanings; }
    public void setMeanings(Meaning2[] value) { this.meanings = value; }

    public String getDefinition() {
        if (meanings != null && meanings.length > 0) {
            Definition2[] defs = meanings[0].getDefinitions();
            if (defs != null && defs.length > 0) {
                return defs[0].getDefinition();
            }
        }
        return "No definition available";
    }

    public String getPartOfSpeech() {
        if (meanings != null && meanings.length > 0) {
            return meanings[0].getPartOfSpeech();
        }
        return "Unknown";
    }

    @Override
    public String toString() {
        return "Dictionary2{" +
                "word='" + word + '\'' +
                ", phonetics=" + Arrays.toString(phonetics) +
                ", meanings=" + Arrays.toString(meanings) +
                '}';
    }

    public static Dictionary2 getDictionary(String word) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + word))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Dictionary2[] results = gson.fromJson(response.body(), Dictionary2[].class);

                if (results != null && results.length > 0) {
                    return results[0];
                }
            } else {
                System.out.println("Dictionary API returned non-200: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Dictionary lookup failed for word '" + word + "': " + e.getMessage());
        }
        return null;
    }
}

// Meaning.java

class Meaning2 {
    private String partOfSpeech;
    private Definition2[] definitions;

    public String getPartOfSpeech() { return partOfSpeech; }
    public void setPartOfSpeech(String value) { this.partOfSpeech = value; }

    public Definition2[] getDefinitions() { return definitions; }
    public void setDefinitions(Definition2[] value) { this.definitions = value; }
}

// Definition.java

class Definition2 {
    private String definition;
    private String example;
    private String[] synonyms;
    private String[] antonyms;

    public String getDefinition() { return definition; }
    public void setDefinition(String value) { this.definition = value; }

    public String getExample() { return example; }
    public void setExample(String value) { this.example = value; }

    public String[] getSynonyms() { return synonyms; }
    public void setSynonyms(String[] value) { this.synonyms = value; }

    public String[] getAntonyms() { return antonyms; }
    public void setAntonyms(String[] value) { this.antonyms = value; }
}

// Phonetic.java


class Phonetic2 {
    private String text;
    private String audio;

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public String getAudio() { return audio; }
    public void setAudio(String value) { this.audio = value; }
}

