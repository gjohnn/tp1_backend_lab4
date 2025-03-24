package com.example.demo.controllers;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import com.example.demo.models.Noticia;
import com.example.demo.repositories.NoticiaRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api//noticias")
@RequiredArgsConstructor
public class NoticiaController {

    private final NoticiaRepository noticiaRepository;
    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public ResponseEntity<?> getNoticias() {
        try {
            return ResponseEntity.ok(noticiaRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar noticias");
        }
    }

    @PostMapping
    public ResponseEntity<?> createNoticia(@RequestBody Noticia noticia) {
        try {
            return ResponseEntity.ok(noticiaRepository.save(noticia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear noticia");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            return ResponseEntity.ok("/" + UPLOAD_DIR + filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al subir la imagen");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editNoticia(@PathVariable("id") Long id, @RequestBody Noticia noticia) {
        try {
            Optional<Noticia> optionalNoticia = noticiaRepository.findById(id);
            if (optionalNoticia.isEmpty()) {
                return ResponseEntity.badRequest().body("Noticia no encontrada");
            }
            Noticia noticiaActualizada = optionalNoticia.get();
            noticiaActualizada.setTitulo(noticia.getTitulo());
            noticiaActualizada.setResumen(noticia.getResumen());
            noticiaActualizada.setImagen(noticia.getImagen());
            noticiaActualizada.setContenidoHtml(noticia.getContenidoHtml());
            noticiaActualizada.setPublicada(noticia.getPublicada());
            noticiaActualizada.setFechaPublicacion(noticia.getFechaPublicacion());
            return ResponseEntity.ok(noticiaRepository.save(noticiaActualizada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al editar noticia");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNoticia(@PathVariable("id") Long id) {
        try {
            if (!noticiaRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("Noticia no encontrada");
            }
            noticiaRepository.deleteById(id);
            return ResponseEntity.ok("Noticia borrada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al borrar noticia");
        }
    }
}
