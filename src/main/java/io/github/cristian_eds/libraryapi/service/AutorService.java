package io.github.cristian_eds.libraryapi.service;


import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> buscar(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(UUID id) {
        autorRepository.deleteById(id);
    }

}
