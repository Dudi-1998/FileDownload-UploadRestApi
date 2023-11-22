package com.ray.api.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ray.api.entity.ImageData;
import com.ray.api.repository.StorageRepository;
import com.ray.api.util.ImageUtils;

@Service
public class StorageService {

    private final StorageRepository repository;

    @Autowired
    public StorageService(StorageRepository repository) {
        this.repository = repository;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        ImageData imageData = repository.save(new ImageData(
                file.getOriginalFilename(),
                file.getContentType(),
                ImageUtils.compressImage(file.getBytes())
        ));

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);

        if (dbImageData.isPresent()) {
            return ImageUtils.decompressImage(dbImageData.get().getImageData());
        } else {
            throw new IllegalArgumentException("Image with name " + fileName + " not found");
        }
    }
}

