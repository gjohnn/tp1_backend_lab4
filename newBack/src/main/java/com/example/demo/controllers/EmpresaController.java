package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import com.example.demo.models.Empresa;
import com.example.demo.repositories.EmpresaRepository;
import java.util.Optional;
@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<?> getEmpresas() {
        try {
            return ResponseEntity.ok(empresaRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmpresa(@RequestBody Empresa empresa) {
        try {
            return ResponseEntity.ok(empresaRepository.save(empresa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear empresa");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editEmpresa(@PathVariable("id") Long id, @RequestBody Empresa empresa) {
        try {
            Optional<Empresa> optionalEmpresa = empresaRepository.findById(id);
            if (optionalEmpresa.isEmpty()) {
                return ResponseEntity.badRequest().body("Empresa no encontrada");
            }
            Empresa empresaActualizada = optionalEmpresa.get();
            empresaActualizada.setDenominacion(empresa.getDenominacion());
            empresaActualizada.setTelefono(empresa.getTelefono());
            empresaActualizada.setHorarioAtencion(empresa.getHorarioAtencion());
            empresaActualizada.setQuienesSomos(empresa.getQuienesSomos());
            empresaActualizada.setLatitud(empresa.getLatitud());
            empresaActualizada.setLongitud(empresa.getLongitud());
            empresaActualizada.setDomicilio(empresa.getDomicilio());
            empresaActualizada.setEmail(empresa.getEmail());
            return ResponseEntity.ok(empresaRepository.save(empresaActualizada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al editar empresa");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable("id") Long id) {
        try {
            if (!empresaRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("Empresa no encontrada");
            }
            empresaRepository.deleteById(id);
            return ResponseEntity.ok("Empresa borrada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al borrar empresa");
        }
    }
}
