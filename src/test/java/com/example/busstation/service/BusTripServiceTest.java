package com.example.busstation.service;

import com.example.busstation.exception.BusTripNotFoundException;
import com.example.busstation.exception.RouteNotFoundForTripException;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.BusTripStatus;
import com.example.busstation.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusTripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private BusTripService busTripService;

    @Test
    void saveReturnsSavedTrip() {
        BusTrip trip = new BusTrip();
        when(tripRepository.save(trip)).thenReturn(trip);

        BusTrip saved = busTripService.save(trip);

        assertThat(saved).isEqualTo(trip);
    }

    // Patch 3: renamed — the catch is over-broad (any RuntimeException), not just FK/route issues
    @Test
    void saveWrapsAnyRepositoryRuntimeExceptionAsRouteNotFoundForTripException() {
        BusTrip trip = new BusTrip();
        when(tripRepository.save(trip)).thenThrow(new RuntimeException("FK violation"));

        assertThatThrownBy(() -> busTripService.save(trip))
                .isInstanceOf(RouteNotFoundForTripException.class);
    }

    @Test
    void findByIdReturnsTripWhenFound() {
        BusTrip trip = new BusTrip();
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        BusTrip result = busTripService.findById(1L);

        assertThat(result).isEqualTo(trip);
    }

    @Test
    void findByIdThrowsBusTripNotFoundExceptionWhenMissing() {
        when(tripRepository.findById(55L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> busTripService.findById(55L))
                .isInstanceOf(BusTripNotFoundException.class);
    }

    // Patch 1: use distinct IDs so BusTrip.equals() (id-based) can distinguish the two instances
    @Test
    void findAllReturnsTripList() {
        BusTrip trip1 = new BusTrip();
        trip1.setId(1L);
        BusTrip trip2 = new BusTrip();
        trip2.setId(2L);
        when(tripRepository.findAll()).thenReturn(List.of(trip1, trip2));

        List<BusTrip> result = busTripService.findAll();

        assertThat(result).hasSize(2).containsExactly(trip1, trip2);
    }

    @Test
    void deleteByIdDelegatesToRepository() {
        busTripService.deleteById(3L);

        verify(tripRepository).deleteById(3L);
    }

    @Test
    void findFilteredAndSortedFallsBackToIdWhenSortFieldIsBlank() {
        when(tripRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        busTripService.findFilteredAndSorted(null, null, "", "asc");

        verify(tripRepository).findFiltered(null, null, Sort.by("id").ascending());
    }

    // Patch 2: null sortField must also fall back to "id"
    @Test
    void findFilteredAndSortedFallsBackToIdWhenSortFieldIsNull() {
        when(tripRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        busTripService.findFilteredAndSorted(null, null, null, "asc");

        verify(tripRepository).findFiltered(null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedSortsDescendingWhenRequested() {
        when(tripRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        busTripService.findFilteredAndSorted("Bucharest", BusTripStatus.PLANNED, "id", "desc");

        verify(tripRepository).findFiltered("Bucharest", BusTripStatus.PLANNED, Sort.by("id").descending());
    }

    @Test
    void findFilteredAndSortedDefaultsToAscendingWhenDirectionIsNull() {
        when(tripRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        busTripService.findFilteredAndSorted(null, BusTripStatus.COMPLETED, "id", null);

        verify(tripRepository).findFiltered(null, BusTripStatus.COMPLETED, Sort.by("id").ascending());
    }

    // Patch 5: all three filter parameters non-null to verify full parameter plumbing
    @Test
    void findFilteredAndSortedPassesAllFiltersToRepository() {
        when(tripRepository.findFiltered(any(), any(), any())).thenReturn(List.of());

        busTripService.findFilteredAndSorted("Cluj", BusTripStatus.ACTIVE, "id", "asc");

        verify(tripRepository).findFiltered("Cluj", BusTripStatus.ACTIVE, Sort.by("id").ascending());
    }

    // Patch 4: findAllSorted — null/blank sortField returns unordered list
    @Test
    void findAllSortedReturnsUnsortedListWhenSortFieldIsNull() {
        BusTrip trip1 = new BusTrip();
        trip1.setId(1L);
        when(tripRepository.findAll()).thenReturn(List.of(trip1));

        List<BusTrip> result = busTripService.findAllSorted(null, "asc");

        assertThat(result).containsExactly(trip1);
        verify(tripRepository).findAll();
        verify(tripRepository, never()).findAll(any(Sort.class));
    }

    @Test
    void findAllSortedReturnsUnsortedListWhenSortFieldIsBlank() {
        when(tripRepository.findAll()).thenReturn(List.of());

        busTripService.findAllSorted("  ", "desc");

        verify(tripRepository).findAll();
        verify(tripRepository, never()).findAll(any(Sort.class));
    }

    @Test
    void findAllSortedSortsAscendingByGivenField() {
        when(tripRepository.findAll(Sort.by("startTime").ascending())).thenReturn(List.of());

        busTripService.findAllSorted("startTime", "asc");

        verify(tripRepository).findAll(Sort.by("startTime").ascending());
    }

    @Test
    void findAllSortedSortsDescendingByGivenField() {
        when(tripRepository.findAll(Sort.by("startTime").descending())).thenReturn(List.of());

        busTripService.findAllSorted("startTime", "desc");

        verify(tripRepository).findAll(Sort.by("startTime").descending());
    }
}
