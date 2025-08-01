package com.postech.challenge_01.mappers;

import com.postech.challenge_01.domains.Address;
import com.postech.challenge_01.dtos.requests.address.AddressRequestDTO;
import com.postech.challenge_01.dtos.requests.address.AddressWithUserRequestDTO;
import com.postech.challenge_01.dtos.responses.AddressResponseDTO;

import java.util.List;

public class AddressMapper {
    public static Address addressRequestDTOToAddress(
            AddressRequestDTO dto
    ) {
        return addressRequestDTOToAddress(null, dto);
    }

    public static Address addressRequestDTOToAddress(
            Long id,
            AddressRequestDTO dto
    ) {
        return new Address(
                id,
                dto.street(),
                dto.number(),
                dto.complement(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.country(),
                dto.postalCode()
        );
    }

    public static AddressResponseDTO addressToAddressResponseDTO(Address entity) {
        return new AddressResponseDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getPostalCode()
        );
    }

    public static List<AddressResponseDTO> addressToAddressResponseDTOList(List<Address> entities) {
        return entities.stream()
                .map(AddressMapper::addressToAddressResponseDTO)
                .toList();
    }

    public static Address addressWithUserRequestDTOToAddress(
            AddressWithUserRequestDTO dto
    ) {
        return new Address(
                null,
                dto.street(),
                dto.number(),
                dto.complement(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.country(),
                dto.postalCode()
        );
    }
}
