package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.demo.models.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    List<Noticia> findTop3ByEmpresaIdOrderByFechaPublicacionDesc(Long empresaId);
    Page<Noticia> findByTituloContainingOrResumenContainingOrderByFechaPublicacionDesc(String titulo, String resumen, Pageable pageable);
}
