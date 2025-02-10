package io.github.cristian_eds.libraryapi.validator;

import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.model.Client;
import io.github.cristian_eds.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClientValidator {

    private final ClientRepository clientRepository;

    public void validar(Client client){
        if(existeClientComMesmoId(client)) throw new RegistroDuplicadoException("Ja existe client com esse id");
    }

    private boolean existeClientComMesmoId(Client client) {
        Client clientEncontrado = clientRepository.findByClientId(client.getClientId());
        return clientEncontrado != null;
    }
}
