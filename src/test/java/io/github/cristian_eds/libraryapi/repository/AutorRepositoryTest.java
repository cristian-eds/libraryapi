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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Ana");
        autor.setDataNascimento(LocalDate.of(2006,11,22));
        autor.setNacionalidade("Brasileira");

        var autorSalvo = autorRepository.save(autor);
        System.out.println(autorSalvo);

    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("5471b2b0-cc68-48fb-afea-ed9d571c134e");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();
            autor.setNome("Joao");
            System.out.println("Dados autor: "+autor);

            autorRepository.save(autor);
        }

    }

    @Test
    public void listarTest() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        long count = autorRepository.count();
        System.out.println("Quantidade de autores: "+count);
    }

    @Test
    public void deletarTest() {
        UUID id = UUID.fromString("a8de0e6b-e7aa-4d6d-b4cf-66ef26ad87ec");
        autorRepository.deleteById(id);
    }

    @Test
    void salvarAutorComLivrosTest() {

        Autor autor = new Autor();
        autor.setNome("Ana");
        autor.setDataNascimento(LocalDate.of(2005,11,22));
        autor.setNacionalidade("Portuguesa");

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setIsbn("8080812");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(2024,5,28));
        livro.setTitulo("Formatura");
        livro.setAutor(autor);

        autor.setLivros(new ArrayList<Livro>());
        autor.getLivros().add(livro);

        autorRepository.save(autor);
    }

    @Test
    void listarLivrosAutor() {
        Autor ana = autorRepository.findById(UUID.fromString("26d8f32a-95c6-4f67-b1c6-80b4a1c7b815")).get();
        livroRepository.findByAutor(ana).forEach(System.out::println);
    }
}
