package com.sabutos.dictionary.model;

/**
 * Created by devil on 27-Nov-16.
 */

public class WordElements {
    private int e_id;
    private String word;
    private String meaning;
    private String synonyms;
    private String example;
    private String type;
    private int a_id;
    
    public WordElements(int e_id, String word, String meaning, String synonyms, String example, String type, int a_id) {
        this.e_id = e_id;
        this.word = word;
        this.meaning = meaning;
        this.synonyms = synonyms;
        this.example = example;
        this.type = type;
        this.a_id = a_id;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }
}
