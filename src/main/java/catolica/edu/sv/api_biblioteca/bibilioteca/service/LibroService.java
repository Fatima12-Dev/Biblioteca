package catolica.edu.sv.api_biblioteca.bibilioteca.service;

import catolica.edu.sv.api_biblioteca.bibilioteca.model.Libro;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class LibroService {

    private final List<Libro> biblioteca = new ArrayList<>();
    private long idCounter = 1;

    public LibroService() {
        biblioteca.add(new Libro(idCounter++,"Don Quijote de la Mancha","Miguel de Cervantes","9788424118044",1605, true));
        biblioteca.add(new Libro(idCounter++, "Cien años de soledad", "Gabriel García Márquez", "9780307474728", 1967, false));
        biblioteca.add(new Libro(idCounter++, "El Principito", "Antoine de Saint-Exupéry", "9781451525984", 1943, true));

    }

    //metodo get general
    public List<Libro> obtenerTodos(){
        return biblioteca;
    }

    //metodo get por ID
    public Optional<Libro> obtenerPorId(long id){
        return biblioteca.stream().filter(libro -> libro.getId().equals(id)).findFirst();
    }

    public List<Libro> getBiblioteca() {
        return biblioteca;
    }
}
