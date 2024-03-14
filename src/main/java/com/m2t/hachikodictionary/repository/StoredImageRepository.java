package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.StoredImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoredImageRepository extends MongoRepository<StoredImage, String> {
}
