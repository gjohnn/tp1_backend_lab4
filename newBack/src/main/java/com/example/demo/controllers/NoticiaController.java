package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import com.example.demo.models.Empresa;
import com.example.demo.models.Noticia;
import com.example.demo.models.NoticiaDTO;
import com.example.demo.repositories.EmpresaRepository;
import com.example.demo.repositories.NoticiaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/noticias")
@RequiredArgsConstructor
public class NoticiaController {

    private final NoticiaRepository noticiaRepository;

    private final EmpresaRepository empresaRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public ResponseEntity<?> getNoticias() {
        try {
            return ResponseEntity.ok(noticiaRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar noticias");
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createNoticia(@ModelAttribute NoticiaDTO noticiaDTO) {
        try {
            // Usamos la fecha actual directamente
            Date fechaPublicacion = new Date(); // Fecha actual

            // Verificar y guardar la imagen
            MultipartFile file = noticiaDTO.getImagen();
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace("..", "");
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                // Asegurarse de que el directorio exista
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists())
                    uploadDir.mkdirs();

                file.transferTo(filePath);

                // Guardar la noticia
                Noticia noticia = new Noticia();
                noticia.setTitulo(noticiaDTO.getTitulo());
                noticia.setResumen(noticiaDTO.getResumen());
                noticia.setContenidoHtml(noticiaDTO.getContenidoHtml());
                noticia.setPublicada(noticiaDTO.getPublicada());
                noticia.setFechaPublicacion(fechaPublicacion); // Asignamos la fecha actual
                noticia.setImagen(filePath.toString()); // Guardamos la ruta de la imagen

                // Obtener la empresa (si es válida)
                Empresa empresa = empresaRepository.findById(noticiaDTO.getIdEmpresa())
                        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
                noticia.setEmpresa(empresa);

                return ResponseEntity.ok(noticiaRepository.save(noticia));
            } else {
                return ResponseEntity.badRequest().body("No se ha enviado una imagen válida.");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al guardar la imagen");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la noticia");
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
