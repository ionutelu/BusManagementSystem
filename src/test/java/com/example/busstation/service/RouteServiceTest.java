package com.example.busstation.service;

import com.example.busstation.exception.DuplicateRouteException;
import com.example.busstation.model.Route;
import com.example.busstation.repository.RouteRepository;
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
class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @Test
    void savePersistsRoute() {
        Route route = new Route();

        routeService.save(route);

        verify(routeRepository).save(route);
    }

    @Test
    void saveThrowsDuplicateRouteExceptionOnConstraintViolation() {
        Route route = new Route();
        doThrow(new DataIntegrityViolationException("duplicate")).when(routeRepository).save(route);

        assertThatThrownBy(() -> routeService.save(route))
                .isInstanceOf(DuplicateRouteException.class);
    }

    @Test
    void findByIdReturnsRouteWhenFound() {
        Route route = new Route();
        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));

        Route result = routeService.findById(1L);

        assertThat(result).isEqualTo(route);
    }

    @Test
    void findByIdThrowsRuntimeExceptionWhenMissing() {
        when(routeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findAllReturnsAllRoutes() {
        when(routeRepository.findAll()).thenReturn(List.of(new Route(), new Route()));

        assertThat(routeService.findAll()).hasSize(2);
    }

    @Test
    void deleteByIdDelegatesToRepository() {
        routeService.deleteById(7L);

        verify(routeRepository).deleteById(7L);
    }

    @Test
    void findFilteredAndSortedNormalizesBlankOriginToNull() {
        when(routeRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        routeService.findFilteredAndSorted("  ", "Cluj", null, "id", "asc");

        verify(routeRepository).findFiltered(null, "Cluj", null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedNormalizesBlankDestinationToNull() {
        when(routeRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        routeService.findFilteredAndSorted("Bucharest", "  ", null, "id", "asc");

        verify(routeRepository).findFiltered("Bucharest", null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedFallsBackToIdWhenSortFieldIsNull() {
        when(routeRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        routeService.findFilteredAndSorted(null, null, null, null, "asc");

        verify(routeRepository).findFiltered(null, null, null, Sort.by("id").ascending());
    }

    @Test
    void findFilteredAndSortedSortsDescendingWhenRequested() {
        when(routeRepository.findFiltered(any(), any(), any(), any())).thenReturn(List.of());

        routeService.findFilteredAndSorted("Bucharest", "Cluj", 150f, "id", "desc");

        verify(routeRepository).findFiltered("Bucharest", "Cluj", 150f, Sort.by("id").descending());
    }
}

