package com.adote.api.infra.dtos.animal.response;

import java.util.List;

public record AnimaisPaginadosDTO(
        List<AnimalResponseDTO> content,
        int currentPage,
        long totalItems,
        int totalPages
) {
}
