package com.ray.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ray.api.entity.ImageData;

public interface StorageRepository extends CrudRepository<ImageData, Long> {
    Optional<ImageData> findByName(String name);
}

