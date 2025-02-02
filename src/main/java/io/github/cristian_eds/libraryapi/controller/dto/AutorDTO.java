package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Campo obrigatorio")
        String nome,
        @NotNull(message = "Campo obrigatorio")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
        String nacionalidade
) {
    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNacionalidade(this.nacionalidade);
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}
