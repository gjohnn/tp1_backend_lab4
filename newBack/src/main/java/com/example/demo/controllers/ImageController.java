package com.example.demo.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {
@GetMapping("/images/uploads/{imageName}")
public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
    try {
        Path imagePath = Paths.get("uploads").resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());
        
        // Detecta el tipo MIME de la imagen
        String mimeType = URLConnection.guessContentTypeFromName(imagePath.toString());
        
        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Si no se puede determinar el tipo, usa un valor genérico
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(mimeType))  // Configura el tipo de contenido dinámicamente
                .body(resource);
    } catch (MalformedURLException e) {
        return ResponseEntity.notFound().build();
    }
}
}