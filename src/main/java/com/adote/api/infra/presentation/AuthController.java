package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.usecases.organizacao.post.CreateOrganizacaoCase;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.dtos.organizacao.request.LoginRequestDTO;
import com.adote.api.infra.dtos.organizacao.request.OrganizacaoRequestDTO;
import com.adote.api.infra.dtos.organizacao.response.LoginResponseDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/adote/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CreateOrganizacaoCase createOrganizacaoCase;
    private final AuthenticationManager authenticationManager;
    private final OrganizacaoMapper organizacaoMapper;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginOrganizacao(@RequestBody LoginRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.senha());
        Authentication authenticate = authenticationManager.authenticate(userAndPass);
        OrganizacaoEntity org = (OrganizacaoEntity) authenticate.getPrincipal();

        String token = tokenService.generateToken(org);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<OrganizacaoResponseDTO> createOrganizacao(@RequestBody OrganizacaoRequestDTO requestDTO) {
        Organizacao newOrganizacao = createOrganizacaoCase.execute(organizacaoMapper.toOrganizacao(requestDTO));
        return ResponseEntity.ok().body(organizacaoMapper.toResponseDTO(newOrganizacao));
    }

}
