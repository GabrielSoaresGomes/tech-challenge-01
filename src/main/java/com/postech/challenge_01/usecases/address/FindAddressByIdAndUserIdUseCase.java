package com.postech.challenge_01.usecases.address;

import com.postech.challenge_01.dtos.requests.address.FindAddressRequestDTO;
import com.postech.challenge_01.dtos.responses.AddressResponseDTO;
import com.postech.challenge_01.exceptions.ResourceNotFoundException;
import com.postech.challenge_01.mappers.AddressMapper;
import com.postech.challenge_01.repositories.address.AddressRepository;
import com.postech.challenge_01.usecases.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindAddressByIdAndUserIdUseCase implements UseCase<FindAddressRequestDTO, AddressResponseDTO> {
    private final AddressRepository addressRepository;

    @Override
    public AddressResponseDTO execute(FindAddressRequestDTO request) {
        var addressId = request.addressId();
        var userId = request.userId();

        log.info("Buscando endereço com ID: {} e ID de usuário {}", userId, addressId);
        var entity = this.addressRepository
                .findByIdAndUserId(userId, addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado para o id " + addressId + " e usuário com id " + userId));

        log.info("Endereço encontrado: {}", entity);
        return AddressMapper.addressToAddressResponseDTO(entity);
    }
}
