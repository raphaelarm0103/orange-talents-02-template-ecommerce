package br.com.mercadolivre.validadors;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.validadors.seguranca.UserDetailsMapper;


@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper {

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario)shouldBeASystemUser);
    }

}