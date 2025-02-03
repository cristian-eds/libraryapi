package io.github.cristian_eds.libraryapi.controller.dto;

import io.github.cristian_eds.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
       String isbn,
       String titulo,
       LocalDate dataPublicacao,
       GeneroLivro genero,
       BigDecimal preco,
       UUID idAutor
) {
}
