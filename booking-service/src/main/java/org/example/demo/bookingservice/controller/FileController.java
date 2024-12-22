package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.model.responses.PhotoResponse;
import org.example.demo.bookingservice.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@Controller
@RequiredArgsConstructor
@RequestMapping(API_VER + "/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("files") MultipartFile[] files, @RequestParam("propertyId") Long propertyId) {
        try {
            for (MultipartFile file : files) {
                fileService.saveFile(file, propertyId);
            }
            return ResponseEntity.ok().build();
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-first/{prop-id}")
    public ResponseEntity<Resource> getFirstPropertyPhotos(@PathVariable("prop-id") Long PropertyId){
        try {
            Resource resource = fileService.loadFile(PropertyId);
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException | IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get/{photo-id}")
    public ResponseEntity<Resource> getPropertyPhotosById(@PathVariable("photo-id") Long photoId){
        try {
            Resource resource = fileService.loadFileById(photoId);
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException | IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-all-ids/{prop-id}")
    public ResponseEntity<PhotoResponse> getAllPropertyPhotos(@PathVariable("prop-id") Long PropertyId){
        try {
            PhotoResponse photoResponse = fileService.getPropPhotoIds(PropertyId);
            return ResponseEntity.ok().body(photoResponse);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
