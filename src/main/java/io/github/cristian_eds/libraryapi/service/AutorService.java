package io.github.cristian_eds.libraryapi.service;


import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

}
