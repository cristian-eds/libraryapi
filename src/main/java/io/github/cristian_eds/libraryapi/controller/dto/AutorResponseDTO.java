package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        UUID id, String nome, LocalDate dataNascimento, String nacionalidade
) {
    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setId(this.id);
        autor.setNacionalidade(this.nacionalidade);
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }

    public static AutorResponseDTO mapearAutorParaAutorResponseDto(Autor autor) {
        return new AutorResponseDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
    }
}
