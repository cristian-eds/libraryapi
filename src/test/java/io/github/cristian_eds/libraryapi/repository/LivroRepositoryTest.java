package io.github.cristian_eds.libraryapi.repository;

import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.model.GeneroLivro;
import io.github.cristian_eds.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setIsbn("1312312");
        livro.setPreco(BigDecimal.valueOf(20));
        livro.setDataPublicacao(LocalDate.of(2020,9,10));
        livro.setTitulo("A volta dos que nao foram");

        Autor autor = autorRepository.findById(UUID.fromString("5471b2b0-cc68-48fb-afea-ed9d571c134e")).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setIsbn("1321");
        livro.setPreco(BigDecimal.valueOf(60));
        livro.setDataPublicacao(LocalDate.of(2022,8,11));
        livro.setTitulo("O trofeu");

        Autor autor = new Autor();
        autor.setNome("Alfredo");
        autor.setDataNascimento(LocalDate.of(1970,4,22));
        autor.setNacionalidade("Sueco");

        livro.setAutor(autor);

        livroRepository.save(livro);
    }


    @Test
    void atualizarAutorLivro() {
        var livroParaAtualizar = livroRepository.findById(UUID.fromString("05d718f9-e6a2-4067-ad3c-3bbcb9a29bbf")).orElse(null);
        Autor autor = autorRepository.findById(UUID.fromString("5471b2b0-cc68-48fb-afea-ed9d571c134e")).orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletarTest() {
        livroRepository.deleteById(UUID.fromString("5b7258f8-d064-471e-a0bd-5634518fe28e"));
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        var livro = livroRepository.findById(UUID.fromString("ea19ee52-1b6d-4a48-90a7-3fb23a13eb3d")).orElse(null);
        System.out.println(livro.getId());
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarLivroPorTitulo() {
        livroRepository.findByTitulo("A volta dos que nao foram").forEach(System.out::println);
    }

    @Test
    void buscarPorGeneroTest() {
        livroRepository.findByGenero(GeneroLivro.FICCAO).forEach(System.out::println);
    }

    @Test
    void deletePorGenero() {
        livroRepository.deleteByGenero(GeneroLivro.FANTASIA);
    }
}