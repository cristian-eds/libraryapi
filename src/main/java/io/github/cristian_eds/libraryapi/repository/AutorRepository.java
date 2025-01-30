package io.github.cristian_eds.libraryapi.repository;

import io.github.cristian_eds.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor,UUID> {
}
