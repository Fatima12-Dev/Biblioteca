package catolica.edu.sv.api_biblioteca.bibilioteca.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(Long id) {
        super("El libro con el ID " + id + " no existe en el sistema.");
    }
}
