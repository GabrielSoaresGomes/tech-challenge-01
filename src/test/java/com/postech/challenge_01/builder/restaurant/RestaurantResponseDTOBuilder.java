package com.postech.challenge_01.builder.restaurant;

import com.postech.challenge_01.dtos.responses.RestaurantResponseDTO;

import java.time.LocalTime;

public class RestaurantResponseDTOBuilder {
    private Long id = 1L;
    private Long ownerId = 1L;
    private Long addressId = 1L;
    private String name = "Restaurante Teste";
    private String type = "Tipo Teste";
    private LocalTime startTime = LocalTime.of(8, 0, 0);
    private LocalTime endTime = LocalTime.of(18, 0, 0);

    public static RestaurantResponseDTOBuilder oneRestaurantResponseDTO() {
        return new RestaurantResponseDTOBuilder();
    }

    public RestaurantResponseDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RestaurantResponseDTOBuilder withOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public RestaurantResponseDTOBuilder withAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public RestaurantResponseDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantResponseDTOBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public RestaurantResponseDTOBuilder withStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public RestaurantResponseDTOBuilder withEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public RestaurantResponseDTO build() {
        return new RestaurantResponseDTO(
                id,
                ownerId,
                addressId,
                name,
                type,
                startTime,
                endTime
        );
    }
}
