package org.hachiko.word;

import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@Schema(name = "Word", description = " WOrd representation")
public class Word extends PanacheMongoEntityBase {
  @BsonProperty("_id")
  @BsonId
  public ObjectId id;
  public String title;
  public Set<String> kind;
  public Set<String> description;
  public Set<String> synonyms;
  public Set<String> sentences;
}
