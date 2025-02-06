package io.github.cristian_eds.libraryapi.service;


import io.github.cristian_eds.libraryapi.exceptions.OperacaoNaoPermitida;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.model.Usuario;
import io.github.cristian_eds.libraryapi.repository.AutorRepository;
import io.github.cristian_eds.libraryapi.repository.LivroRepository;
import io.github.cristian_eds.libraryapi.security.SecurityService;
import io.github.cristian_eds.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja cadastrado.");
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> buscar(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(UUID id) {
        if(possuiLivro(id)) throw new OperacaoNaoPermitida("Autor possui livros cadastados vinculados!");
        autorRepository.deleteById(id);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return autorRepository.findByNome(nome);
        }
        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor =  new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(UUID id) {
        Autor autor = autorRepository.findById(id).get();
        return livroRepository.existsByAutor(autor);
    }

}
