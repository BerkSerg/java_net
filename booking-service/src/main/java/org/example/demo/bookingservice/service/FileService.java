package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.example.demo.bookingservice.dto.PropertyPhotoDto;
import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.PropertyPhotos;
import org.example.demo.bookingservice.model.responses.PhotoResponse;
import org.example.demo.bookingservice.repository.FileRepository;
import org.example.demo.bookingservice.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final PropertyRepository propertyRepository;

    @Value("${file.storage}")
    private String STORAGE_PATH;

    @SneakyThrows
    public void saveFile(MultipartFile file, Long propertyId) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        PropertyPhotos propertyPhotos = PropertyPhotos.builder()
                .property(propertyRepository.getById(propertyId))
                .originalFileName(file.getOriginalFilename())
                .storageFileName(UUID.randomUUID() + "." + ext)
                .extension(ext)
                .size(file.getSize())
                .build();
        fileRepository.save(propertyPhotos);
        Files.copy(file.getInputStream(), Path.of(STORAGE_PATH + "/" + propertyPhotos.getStorageFileName()));
    }


    public Resource loadFile(Long propertyId) throws RuntimeException, IOException {
        Property property = propertyRepository.getById(propertyId);
        PropertyPhotos propertyPhoto = fileRepository.findFirstByProperty(property);
        String fileName;
        if(propertyPhoto==null){
            fileName="noimage2.webp";
        }else{
            fileName=propertyPhoto.getStorageFileName();
        }

        Path filePath = Paths.get(STORAGE_PATH).resolve(fileName).normalize();
        if (Files.exists(filePath)) {
            return new UrlResource(filePath.toUri());
        }else{
            filePath = Paths.get(STORAGE_PATH).resolve("noimage2.webp").normalize();
            return new UrlResource(filePath.toUri());
        }
    }


    public PhotoResponse getPropPhotoIds(Long propertyId) {
        Property property = propertyRepository.getById(propertyId);
        List<PropertyPhotos> propPhotos = fileRepository.findAllByProperty(property);

        List<Long> photoIds = propPhotos.stream().map(PropertyPhotos::getId).toList();
        return PhotoResponse.builder()
                .photos(photoIds)
                .totalPhotos(photoIds.size())
                .build();
    }

    public Resource loadFileById(Long photoId) throws MalformedURLException {
        PropertyPhotos propertyPhoto = fileRepository.getById(photoId);
        String fileName;
        if(propertyPhoto==null){
            fileName="noimage2.webp";
        }else{
            fileName=propertyPhoto.getStorageFileName();
        }

        Path filePath = Paths.get(STORAGE_PATH).resolve(fileName).normalize();
        if (Files.exists(filePath)) {
            return new UrlResource(filePath.toUri());
        }else{
            filePath = Paths.get(STORAGE_PATH).resolve("noimage2.webp").normalize();
            return new UrlResource(filePath.toUri());
        }

    }
}
