package org.hachiko.word;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Word extends PanacheMongoEntity {
  public String title;
  public String[] kind;
  public String[] description;
  public String[] synonyms;
  public String[] sentences;

  public Word() {

  }

  public Word(String title, String[] kind, String[] description, String[] synonyms, String[] sentences) {
    this.title = title;
    this.kind = kind;
    this.description = description;
    this.synonyms = synonyms;
  }
}
