package dev.pronunciationAppBack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.List;
import java.util.Set;

@Entity
public class Word {

    @Id
    private String id;
    private String wordName;
    private String definition;
    private String phoneticSpelling;
    private String sentence;
    private boolean isActive;
    private int level;

    @OneToMany(mappedBy = "word")
    private List<Pronunciation> pronunciations;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "word_category",
            joinColumns = {@JoinColumn(name = "CATEGORY_FK")},
    inverseJoinColumns = {@JoinColumn(name = "WORD_FK")})
    private Set<Category> categories;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_ID")
    private Set<Level> levels;



    public Word() {}

    public Word(String id, String wordName, String definition, String phoneticSpelling, String sentence, boolean isActive, int level) {
        this.id = id;
        this.wordName = wordName;
        this.definition = definition;
        this.phoneticSpelling = phoneticSpelling;
        this.sentence = sentence;
        this.isActive = isActive;
        this.level = level;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public void setPronunciations(List<Pronunciation> pronunciations) {
        this.pronunciations = pronunciations;
    }

    public String getId() {
        return id;
    }

    public String getWordName() {
        return wordName;
    }

    public String getDefinition() {
        return definition;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public String getSentence() {
        return sentence;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getLevel() {
        return level;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        this.phoneticSpelling = phoneticSpelling;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", wordName='" + wordName + '\'' +
                ", definition='" + definition + '\'' +
                ", phoneticSpelling='" + phoneticSpelling + '\'' +
                ", sentence='" + sentence + '\'' +
                ", isActive=" + isActive +
                ", level=" + level +
                '}';
    }
}
