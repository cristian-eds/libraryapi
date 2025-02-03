package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Campo obrigatorio")
                @Size(max = 100, min = 2,message = "Campo fora do tamanho padrao.")
        String nome,
        @NotNull(message = "Campo obrigatorio")
                @Past(message = "Nao pode ser data futura.")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
                @Size(max = 50,  min = 2, message = "Campo fora do tamanho padrao")
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
