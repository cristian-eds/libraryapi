package io.github.cristian_eds.libraryapi.service;


import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.repository.AutorRepository;
import io.github.cristian_eds.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
    }

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
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

}
