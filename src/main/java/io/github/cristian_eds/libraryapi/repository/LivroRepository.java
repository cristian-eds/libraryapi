package io.github.cristian_eds.libraryapi.repository;

import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.model.GeneroLivro;
import io.github.cristian_eds.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    boolean existsByAutor(Autor autor);

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodos();

    @Query("select l from Livro as l where l.genero = :genero")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro genero);


    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate dataPublicacao);



}
