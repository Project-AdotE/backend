package com.adote.api.infra.config.auth;

import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final OrganizacaoRepository organizacaoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return organizacaoRepository.getOrganizacaoEntityByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha não encontrados"));
    }
}
