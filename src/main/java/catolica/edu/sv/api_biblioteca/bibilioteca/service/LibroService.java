package catolica.edu.sv.api_biblioteca.bibilioteca.service;

import catolica.edu.sv.api_biblioteca.bibilioteca.dto.LibroRequestDTO;
import catolica.edu.sv.api_biblioteca.bibilioteca.exception.IsbnDuplicadoException;
import catolica.edu.sv.api_biblioteca.bibilioteca.exception.LibroNoEncontradoException;
import catolica.edu.sv.api_biblioteca.bibilioteca.model.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final List<Libro> biblioteca = new ArrayList<>();
    private long idCounter = 1;

    public LibroService() {
        biblioteca.add(new Libro(idCounter++, "Don Quijote de la Mancha", "Miguel de Cervantes", "9788424118044", 1605, true));
        biblioteca.add(new Libro(idCounter++, "Cien años de soledad", "Gabriel García Márquez", "9780307474728", 1967, false));
        biblioteca.add(new Libro(idCounter++, "El Principito", "Antoine de Saint-Exupéry", "9781451525984", 1943, true));
    }

    public List<Libro> obtenerTodos() {
        return biblioteca;
    }

    public Optional<Libro> obtenerPorId(long id) {
        return biblioteca.stream().filter(libro -> libro.getId().equals(id)).findFirst();
    }

    public List<Libro> getBiblioteca() {
        return biblioteca;
    }

    public Libro crear(LibroRequestDTO request) {
        if (existeIsbn(request.getIsbn(), null)) {
            throw new IsbnDuplicadoException(request.getIsbn());
        }

        Libro libro = new Libro();
        libro.setId(idCounter++);
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setIsbn(request.getIsbn());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setDisponible(request.getDisponible() == null || request.getDisponible());

        biblioteca.add(libro);
        return libro;
    }

    /**
     * Actualiza un libro existente.
     * Valida que el libro exista y que el nuevo ISBN no pertenezca a otro registro.
     */
    public Libro actualizar(Long id, LibroRequestDTO request) {
        Libro libro = obtenerPorId(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));

        if (existeIsbn(request.getIsbn(), id)) {
            throw new IsbnDuplicadoException(request.getIsbn());
        }

        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setIsbn(request.getIsbn());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        if (request.getDisponible() != null) {
            libro.setDisponible(request.getDisponible());
        }
        return libro;
    }

    /**
     * Elimina un libro por su ID. Si no existe, lanza 404.
     */
    public void eliminar(Long id) {
        Libro libro = obtenerPorId(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
        biblioteca.remove(libro);
    }

    /**
     * Verifica si el ISBN ya esta registrado.
     * Si idExcluido != null, se ignora ese registro (para no chocar consigo mismo al actualizar).
     */
    private boolean existeIsbn(String isbn, Long idExcluido) {
        return biblioteca.stream()
                .anyMatch(libro -> libro.getIsbn().equalsIgnoreCase(isbn)
                        && (idExcluido == null || !libro.getId().equals(idExcluido)));
    }
}
