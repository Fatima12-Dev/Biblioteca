package catolica.edu.sv.api_biblioteca.bibilioteca.controller;

import catolica.edu.sv.api_biblioteca.bibilioteca.dto.LibroResponseDTO;
import catolica.edu.sv.api_biblioteca.bibilioteca.model.Libro;
import catolica.edu.sv.api_biblioteca.bibilioteca.service.LibroService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/libros")


public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    //endpoint get para listar los libros y filtro de disponibilidad
    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> listarLibros(
            @RequestParam(required = false) Boolean disponible) {
        List<Libro> libros = libroService.obtenerTodos();
        if (disponible != null) {
            libros = libros.stream()
                    .filter(libro -> disponible.equals(libro.isDisponible()))
                    .collect(Collectors.toList());
        }
        List<LibroResponseDTO> respuestaDto = libros.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());

        // Retornamos la lista con un estado HTTP 200 OK
        return ResponseEntity.ok(respuestaDto);
    }

    //endpoint get por medio de ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id)
                .map(libro -> ResponseEntity.ok(convertirADto(libro)))
                .orElseThrow(() -> new RuntimeException("El libro con el ID " + id + " no existe en el sistema."));
    }

    //convertir el modelo en dto
    private LibroResponseDTO convertirADto(Libro libro) {
        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setIsbn(libro.getIsbn());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setDisponible(libro.isDisponible());
        return dto;
    }

}
