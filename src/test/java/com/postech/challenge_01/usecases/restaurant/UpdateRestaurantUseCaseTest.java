package com.postech.challenge_01.usecases.restaurant;

import com.postech.challenge_01.builder.restaurant.RestaurantBuilder;
import com.postech.challenge_01.builder.restaurant.RestaurantResponseDTOBuilder;
import com.postech.challenge_01.builder.restaurant.RestaurantUpdateRequestDTOBuilder;
import com.postech.challenge_01.domains.Restaurant;
import com.postech.challenge_01.dtos.requests.restaurant.RestaurantUpdateRequestDTO;
import com.postech.challenge_01.dtos.responses.RestaurantResponseDTO;
import com.postech.challenge_01.exceptions.RestaurantNotFoundException;
import com.postech.challenge_01.repositories.restaurant.RestaurantRepository;
import com.postech.challenge_01.usecases.rules.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UpdateRestaurantUseCaseTest {
    private AutoCloseable closeable;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private Rule<Restaurant> ruleMock;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantRepository, List.of(ruleMock));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldExecuteAndUpdateRestaurantSuccessfully() {
        // Arrange
        Long id = 1L;
        RestaurantUpdateRequestDTO requestDTO = RestaurantUpdateRequestDTOBuilder
                .oneRestaurantUpdateRequestDTO().build();

        Restaurant updatedRestaurant = RestaurantBuilder
                .oneRestaurant().withId(id).build();

        RestaurantResponseDTO expectedResponse = RestaurantResponseDTOBuilder
                .oneRestaurantResponseDTO().withId(id).build();

        when(restaurantRepository.update(any(Restaurant.class), anyLong())).thenReturn(Optional.of(updatedRestaurant));

        // Act
        RestaurantResponseDTO response = updateRestaurantUseCase.execute(requestDTO);

        // Assert
        verify(restaurantRepository, times(1)).update(any(Restaurant.class), eq(id));
        verify(ruleMock).execute(any(Restaurant.class));

        assertThat(response).isEqualTo(expectedResponse);
        assertThat(response.id()).isEqualTo(id);
    }

    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExist() {
        // Arrange
        Long id = 1L;
        RestaurantUpdateRequestDTO requestDTO = RestaurantUpdateRequestDTOBuilder
                .oneRestaurantUpdateRequestDTO().build();

        when(restaurantRepository.update(any(Restaurant.class), anyLong())).thenReturn(Optional.empty());

        // Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                () -> updateRestaurantUseCase.execute(requestDTO));

        assertThat(exception.getMessage()).isEqualTo("Restaurante com ID " + id + " não foi encontrado");
        verify(restaurantRepository, times(1)).update(any(Restaurant.class), eq(id));
    }
}
