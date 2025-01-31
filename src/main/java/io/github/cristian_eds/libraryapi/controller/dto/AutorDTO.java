package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.Autor;
import java.time.LocalDate;

public record AutorDTO(
        String nome, LocalDate dataNascimento, String nacionalidade
) {
    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNacionalidade(this.nacionalidade);
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}
