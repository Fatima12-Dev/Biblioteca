package catolica.edu.sv.api_biblioteca.bibilioteca.exception;

public class IsbnDuplicadoException extends RuntimeException {
    public IsbnDuplicadoException(String isbn) {
        super("Ya existe un libro registrado con el ISBN: " + isbn);
    }
}
