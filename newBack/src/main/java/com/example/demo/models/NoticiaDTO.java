package com.example.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class NoticiaDTO {
    private Long id;

    private String titulo;
    private String resumen;
    private String contenidoHtml;
    private char publicada;
    private MultipartFile imagen;
    private Long idEmpresa;
    
}
