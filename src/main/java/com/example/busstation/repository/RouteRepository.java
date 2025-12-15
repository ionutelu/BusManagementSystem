package com.example.busstation.repository;
import com.example.busstation.model.Route;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("""
        SELECT r FROM Route r
        WHERE (:origin IS NULL OR LOWER(r.origin.name) LIKE LOWER(CONCAT('%', :origin, '%')))
          AND (:destination IS NULL OR LOWER(r.destination.name) LIKE LOWER(CONCAT('%', :destination, '%')))
          AND (:maxDistance IS NULL OR r.distance <= :maxDistance)
    """)
    List<Route> findFiltered(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("maxDistance") Float maxDistance,
            Sort sort
    );}
