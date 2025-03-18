package com.adote.api.infra.dtos.qrCodePix.request;

public record QrCodeRequestDTO(
        String tipo,
        String chave,
        String nome,
        String cidade
) {
}
