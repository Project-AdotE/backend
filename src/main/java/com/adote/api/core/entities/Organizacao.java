package com.adote.api.core.entities;

import java.util.List;

public record Organizacao(Long id,
                          String nome,
                          String numero,
                          String cnpj,
                          EnderecoOrganizacao endereco,
                          List<ChavePix> chavesPix,
                          String email,
                          String senha) {
}
