package com.bonheur.domain.image.service;

import com.bonheur.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
}
