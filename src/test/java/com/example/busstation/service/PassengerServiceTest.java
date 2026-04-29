package com.example.busstation.service;

import com.example.busstation.exception.PassengerNotFoundException;
import com.example.busstation.model.Passenger;
import com.example.busstation.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void savePersistsAndReturnsPassenger() {
        Passenger passenger = new Passenger("Alice", "EUR", Collections.emptyList());
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger saved = passengerService.save(passenger);

        assertThat(saved).isEqualTo(passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void findByIdReturnsPassengerWhenFound() {
        Passenger passenger = new Passenger("Bob", "USD", Collections.emptyList());
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Passenger result = passengerService.findById(1L);

        assertThat(result.getName()).isEqualTo("Bob");
    }

    @Test
    void findByIdThrowsPassengerNotFoundExceptionWhenMissing() {
        when(passengerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> passengerService.findById(99L))
                .isInstanceOf(PassengerNotFoundException.class);
    }

    @Test
    void findAllReturnsAllPassengers() {
        List<Passenger> passengers = List.of(
                new Passenger("Alice", "EUR", Collections.emptyList()),
                new Passenger("Bob", "USD", Collections.emptyList())
        );
        when(passengerRepository.findAll()).thenReturn(passengers);

        List<Passenger> result = passengerService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteByIdDelegatestoRepository() {
        passengerService.deleteById(5L);

        verify(passengerRepository).deleteById(5L);
    }

    @Test
    void findFilteredAndSortedNormalizesBlankNameToNull() {
        when(passengerRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        passengerService.findFilteredAndSorted("  ", null, "id", "asc");

        verify(passengerRepository).findFiltered(null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedNormalizesBlankCurrencyToNull() {
        when(passengerRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        passengerService.findFilteredAndSorted(null, "", "id", "asc");

        verify(passengerRepository).findFiltered(null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedFallsBackToIdWhenSortFieldIsNull() {
        when(passengerRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        passengerService.findFilteredAndSorted(null, null, null, "asc");

        verify(passengerRepository).findFiltered(null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedSortsDescendingWhenRequested() {
        when(passengerRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        passengerService.findFilteredAndSorted("Alice", "EUR", "name", "desc");

        verify(passengerRepository).findFiltered("Alice", "EUR", Sort.by("name").descending());
    }
}

