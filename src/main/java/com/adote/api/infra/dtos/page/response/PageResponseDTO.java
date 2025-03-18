package com.adote.api.infra.dtos.page.response;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        int currentPage,
        long totalItems,
        int totalPages
) {}
