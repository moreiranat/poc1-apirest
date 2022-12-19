package com.api.nataly.poc1api.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    @Getter
    private String field;
    @Getter
    private String error;
}
