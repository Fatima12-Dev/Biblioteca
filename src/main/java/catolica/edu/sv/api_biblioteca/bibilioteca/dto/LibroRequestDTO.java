package catolica.edu.sv.api_biblioteca.bibilioteca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LibroRequestDTO {

    @NotBlank(message = "El titulo es obligatorio y no puede estar vacio.")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio y no puede estar vacio.")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio y no puede estar vacio.")
    @Pattern(
            regexp = "^[0-9Xx-]{10,17}$",
            message = "El ISBN debe contener entre 10 y 17 caracteres (digitos, guiones o X)."
    )
    private String isbn;

    @NotNull(message = "El anio de publicacion es obligatorio.")
    @Min(value = 1001, message = "El anio de publicacion debe ser mayor a 1000.")
    private Integer anioPublicacion;

    private Boolean disponible;
}
