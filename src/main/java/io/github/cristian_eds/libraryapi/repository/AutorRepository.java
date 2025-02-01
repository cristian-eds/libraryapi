package io.github.cristian_eds.libraryapi.repository;

import io.github.cristian_eds.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,UUID> {

    List<Autor> findByNome(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndNacionalidadeAndDataNascimento(String nome, String nacionalidade, LocalDate dataNascimento);
}
