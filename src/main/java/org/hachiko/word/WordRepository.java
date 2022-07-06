package org.hachiko.word;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class WordRepository implements PanacheMongoRepository<Word> {
  public Word findByTitle(String title) {
    return find("title", title).firstResult();
  }
}
