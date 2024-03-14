package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.controller.StoredImageController;
import com.m2t.hachikodictionary.dto.storedimage.CreateStoredImageRequest;
import com.m2t.hachikodictionary.model.StoredImage;
import com.m2t.hachikodictionary.repository.StoredImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StoredImageService {
    private final StoredImageRepository storedImageRepository;

    public StoredImageService(StoredImageRepository storedImageRepository) {
        this.storedImageRepository = storedImageRepository;
    }

    public Page<StoredImage> findAllByPage(Pageable pageable) {
        return storedImageRepository.findAll(pageable);
    }

    public StoredImage createStoredImage(CreateStoredImageRequest request) {
        StoredImage storedImage = new StoredImage();
        storedImage.setUrl(request.getUrl());
        storedImage.setDate(request.getDate());
        storedImage.setUploadedAt(LocalDateTime.now());
        return storedImageRepository.save(storedImage);
    }
}
