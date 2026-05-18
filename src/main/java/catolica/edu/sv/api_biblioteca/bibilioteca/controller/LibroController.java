package catolica.edu.sv.api_biblioteca.bibilioteca.controller;

import catolica.edu.sv.api_biblioteca.bibilioteca.dto.LibroRequestDTO;
import catolica.edu.sv.api_biblioteca.bibilioteca.dto.LibroResponseDTO;
import catolica.edu.sv.api_biblioteca.bibilioteca.exception.LibroNoEncontradoException;
import catolica.edu.sv.api_biblioteca.bibilioteca.model.Libro;
import catolica.edu.sv.api_biblioteca.bibilioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }


    // GET /api/v1/libros          -> lista todos
    // GET /api/v1/libros?disponible=true -> filtra por disponibilidad
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

        return ResponseEntity.ok(respuestaDto);
    }

    // GET /api/v1/libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id) {
        Libro libro = libroService.obtenerPorId(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
        return ResponseEntity.ok(convertirADto(libro));
    }


    // POST /api/v1/libros  -> 201 Created
    @PostMapping
    public ResponseEntity<LibroResponseDTO> registrar(@Valid @RequestBody LibroRequestDTO request) {
        Libro creado = libroService.crear(request);
        URI location = URI.create("/api/v1/libros/" + creado.getId());
        return ResponseEntity.created(location).body(convertirADto(creado));
    }

    // PUT /api/v1/libros/{id} -> 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LibroRequestDTO request) {
        Libro actualizado = libroService.actualizar(id, request);
        return ResponseEntity.ok(convertirADto(actualizado));
    }

    // DELETE /api/v1/libros/{id} -> 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }

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
