package io.github.cristian_eds.libraryapi.service;

import io.github.cristian_eds.libraryapi.model.Usuario;
import io.github.cristian_eds.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario) {
        var senha = usuario.getSenha();
        System.out.println("Senha: "+senha);
        usuario.setSenha(passwordEncoder.encode(senha));
        System.out.println("Service: "+usuario);
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
