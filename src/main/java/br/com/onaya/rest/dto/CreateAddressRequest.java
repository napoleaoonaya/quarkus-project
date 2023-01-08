package br.com.onaya.rest.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateAddressRequest(
        @NotBlank(message="Address not empty")
        @NotNull(message="Address required")
        String address,
        @NotNull(message="Number House required")
        Long numberHouse)
{}

