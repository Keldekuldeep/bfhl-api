package com.acropolis.bfhl.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BfhlRequest {

    @NotNull(message = "data field must not be null")
    @NotEmpty(message = "data array must not be empty")
    private List<String> data;
}
