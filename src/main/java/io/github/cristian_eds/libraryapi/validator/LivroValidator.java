package io.github.cristian_eds.libraryapi.validator;

import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.exceptions.RegraDeNegocioException;
import io.github.cristian_eds.libraryapi.model.Livro;
import io.github.cristian_eds.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro) {
        if(existeLivroComMesmoIsbn(livro)) throw new RegistroDuplicadoException("Já existe um livro com esse mesmo ISBN.");
        if(precoObrigatorioEstaNulo(livro)) throw new RegraDeNegocioException("preco","Preço é obrigatório quando data de duplicação do livro for posterior a 2020.");
    }

    private boolean precoObrigatorioEstaNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComMesmoIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null) return livroEncontrado.isPresent();

        return !livro.getId().equals(livroEncontrado.get().getId());
    }
}
