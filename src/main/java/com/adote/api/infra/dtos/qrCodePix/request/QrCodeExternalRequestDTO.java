package com.adote.api.infra.dtos.qrCodePix.request;

public record QrCodeExternalRequestDTO(
        String key_type,
        String key,
        String name,
        String city,
        String amount,
        String reference
) {
}
