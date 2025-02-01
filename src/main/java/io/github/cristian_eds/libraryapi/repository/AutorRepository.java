package io.github.cristian_eds.libraryapi.repository;

import io.github.cristian_eds.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,UUID> {

    List<Autor> findByNome(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
}
