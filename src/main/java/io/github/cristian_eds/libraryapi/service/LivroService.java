package io.github.cristian_eds.libraryapi.service;

import io.github.cristian_eds.libraryapi.model.Livro;
import io.github.cristian_eds.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
