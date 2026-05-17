package catolica.edu.sv.api_biblioteca.bibilioteca.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "titulo", "autor", "isbn", "anioPublicacion", "disponible" })

@Data
public class LibroResponseDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anioPublicacion;
    private boolean disponible;
}
