package io.github.cristian_eds.libraryapi.validator;

import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository repository) {
        this.autorRepository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorJaCadastrado(autor)) throw  new RegistroDuplicadoException("Autor já cadastrado com essas informações.");

    }

    private boolean existeAutorJaCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndNacionalidadeAndDataNascimento(
                autor.getNome(),autor.getNacionalidade(), autor.getDataNascimento()
        );

        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
