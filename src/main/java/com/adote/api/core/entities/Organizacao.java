package com.adote.api.core.entities;

public record Organizacao(Long id,
                          String nome,
                          String numero,
                          String cnpj,
                          String cep,
                          String email,
                          String senha) {
}
