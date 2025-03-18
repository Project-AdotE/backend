package com.adote.api.infra.dtos.qrCodePix.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QrCodeResponseDTO(

        @JsonProperty("qrcode_base64")
        String qrCode
) {
}
