package com.example.busstation.service;

import com.example.busstation.exception.BusCapacityInvalid;
import com.example.busstation.exception.BusNotFoundException;
import com.example.busstation.exception.DuplicateRegistrationException;
import com.example.busstation.exception.DuplicateVinException;
import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStatus;
import com.example.busstation.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    private Bus validBus;

    @BeforeEach
    void setUp() {
        validBus = new Bus("B-01-ABC", 50, "VIN123456");
        validBus.setStatus(BusStatus.ACTIVE);
    }

    @Test
    void savesValidBusSuccessfully() {
        when(busRepository.save(validBus)).thenReturn(validBus);

        Bus saved = busService.save(validBus);

        assertThat(saved).isEqualTo(validBus);
        verify(busRepository).save(validBus);
    }

    @Test
    void saveBusWithCapacityAtLowerBoundSucceeds() {
        validBus.setCapacity(20);
        when(busRepository.save(validBus)).thenReturn(validBus);

        assertThatNoException().isThrownBy(() -> busService.save(validBus));
    }

    @Test
    void saveBusWithCapacityAtUpperBoundSucceeds() {
        validBus.setCapacity(80);
        when(busRepository.save(validBus)).thenReturn(validBus);

        assertThatNoException().isThrownBy(() -> busService.save(validBus));
    }

    @Test
    void saveBusWithCapacityBelowMinimumThrowsBusCapacityInvalid() {
        validBus.setCapacity(19);

        assertThatThrownBy(() -> busService.save(validBus))
                .isInstanceOf(BusCapacityInvalid.class);

        verify(busRepository, never()).save(any());
    }

    @Test
    void saveBusWithCapacityAboveMaximumThrowsBusCapacityInvalid() {
        validBus.setCapacity(81);

        assertThatThrownBy(() -> busService.save(validBus))
                .isInstanceOf(BusCapacityInvalid.class);

        verify(busRepository, never()).save(any());
    }

    @Test
    void saveBusWithDuplicateVinThrowsDuplicateVinException() {
        DataIntegrityViolationException cause = new DataIntegrityViolationException("uq_vin");
        when(busRepository.save(validBus)).thenThrow(cause);

        assertThatThrownBy(() -> busService.save(validBus))
                .isInstanceOf(DuplicateVinException.class);
    }

    @Test
    void saveBusWithDuplicateRegistrationNumberThrowsDuplicateRegistrationException() {
        DataIntegrityViolationException cause = new DataIntegrityViolationException("uq_registration_number");
        when(busRepository.save(validBus)).thenThrow(cause);

        assertThatThrownBy(() -> busService.save(validBus))
                .isInstanceOf(DuplicateRegistrationException.class);
    }

    @Test
    void findByIdReturnsBusWhenFound() {
        when(busRepository.findById(1L)).thenReturn(Optional.of(validBus));

        Bus result = busService.findById(1L);

        assertThat(result).isEqualTo(validBus);
    }

    @Test
    void findByIdThrowsBusNotFoundExceptionWhenMissing() {
        when(busRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> busService.findById(99L))
                .isInstanceOf(BusNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findAllReturnsBusList() {
        Bus second = new Bus("B-02-DEF", 30, "VIN999");
        when(busRepository.findAll()).thenReturn(List.of(validBus, second));

        List<Bus> result = busService.findAll();

        assertThat(result).hasSize(2).contains(validBus, second);
    }

    @Test
    void deleteByIdRemovesBusWhenItExists() {
        when(busRepository.existsById(1L)).thenReturn(true);

        busService.deleteById(1L);

        verify(busRepository).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsBusNotFoundExceptionWhenBusMissing() {
        when(busRepository.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> busService.deleteById(42L))
                .isInstanceOf(BusNotFoundException.class)
                .hasMessageContaining("42");

        verify(busRepository, never()).deleteById(any());
    }

    @Test
    void findAllFilteredAndSortedUsesIdAsFallbackWhenSortFieldIsBlank() {
        when(busRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        busService.findAllFilteredAndSorted(null, null, null, "", "asc");

        verify(busRepository).findFiltered(null, null, null, Sort.by("id").ascending());
    }

    @Test
    void findAllFilteredAndSortedSortsDescendingWhenRequested() {
        when(busRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        busService.findAllFilteredAndSorted(null, BusStatus.ACTIVE, 30, "capacity", "desc");

        verify(busRepository).findFiltered(null, BusStatus.ACTIVE, 30, Sort.by("capacity").descending());
    }

    @Test
    void findAllFilteredAndSortedSortsAscendingByDefault() {
        when(busRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        busService.findAllFilteredAndSorted("VIN", null, null, "vin", null);

        verify(busRepository).findFiltered("VIN", null, null, Sort.by("vin").ascending());
    }
}

