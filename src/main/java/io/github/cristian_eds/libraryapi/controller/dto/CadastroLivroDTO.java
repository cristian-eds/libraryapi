package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "Campo obrigatorio")
        @ISBN
        String isbn,

        @NotBlank(message = "Campo obrigatorio")
        String titulo,

        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Nao pode ser data futura.")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,

        @NotNull(message = "Campo obrigatorio")
        UUID idAutor
) {
}
