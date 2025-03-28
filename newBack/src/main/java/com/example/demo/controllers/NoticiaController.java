package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.List;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticia(@PathVariable("id") Long id) {
        try {
            Optional<Noticia> optionalNoticia = noticiaRepository.findById(id);
            if (optionalNoticia.isEmpty()) {
                return ResponseEntity.badRequest().body("Noticia no encontrada");
            }
            return ResponseEntity.ok(optionalNoticia.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar noticia");
        }
    }

    @GetMapping("/{id}/ultimas")
    public ResponseEntity<?> getUltimasNoticiasPorEmpresa(@PathVariable("id") Long idEmpresa) {
        try {
            // Obtener las últimas 3 noticias de la empresa ordenadas por fecha de publicación
            List<Noticia> ultimasNoticias = noticiaRepository.findTop3ByEmpresaIdOrderByFechaPublicacionDesc(idEmpresa);
            return ResponseEntity.ok(ultimasNoticias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar las últimas noticias de la empresa");
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarNoticias(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
        ) {
            try {
                // Creamos un Pageable para la paginación
                Pageable pageable = PageRequest.of(page, size);

                // Buscamos las noticias que coincidan con el término de búsqueda
                Page<Noticia> noticias = noticiaRepository.findByTituloContainingOrResumenContainingOrderByFechaPublicacionDesc(query, query, pageable);

                return ResponseEntity.ok(noticias);
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
