package com.adote.api.core.entities;

public record Organizacao(Long id,
                          String nome,
                          String numero,
                          String cnpj,
                          EnderecoOrganizacao endereco,
                          String email,
                          String senha) {
}
