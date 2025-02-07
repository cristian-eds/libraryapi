package io.github.cristian_eds.libraryapi.security;

import io.github.cristian_eds.libraryapi.model.Usuario;
import io.github.cristian_eds.libraryapi.service.CustomAuthentication;
import io.github.cristian_eds.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaInformada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);

        if (usuarioEncontrado == null) throw new UsernameNotFoundException("Usuário ou senha incorretos.");

        String senhaEncriptografada = usuarioEncontrado.getSenha();

        boolean senhasConferem = passwordEncoder.matches(senhaInformada, senhaEncriptografada);

        if (senhasConferem) return new CustomAuthentication(usuarioEncontrado);

        throw new UsernameNotFoundException("Usuário ou senha incorretos.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
