package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.storedimage.CreateStoredImageRequest;
import com.m2t.hachikodictionary.model.StoredImage;
import com.m2t.hachikodictionary.service.StoredImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stored-image")
public class StoredImageController {
    private final StoredImageService storedImageService;

    public StoredImageController(StoredImageService storedImageService) {
        this.storedImageService = storedImageService;
    }

    @GetMapping("/list")
    public Page<StoredImage> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "8") int sizePerPage) {
        Pageable pageable = PageRequest.of(page, sizePerPage, Sort.by("date").descending());
        return storedImageService.findAllByPage(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<StoredImage> createStoredImage(@RequestBody CreateStoredImageRequest request) {
        return ResponseEntity.ok(storedImageService.createStoredImage(request));
    }
}
