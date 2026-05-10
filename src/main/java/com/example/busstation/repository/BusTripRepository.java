package com.example.busstation.repository;

import com.example.busstation.model.BusTrip;
import com.example.busstation.model.BusTripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusTripRepository extends JpaRepository<BusTrip, Long> {

    @Query("""
    SELECT bt FROM BusTrip bt
    WHERE (
        :route IS NULL OR
        LOWER(bt.route.origin.name) LIKE LOWER(CONCAT('%', :route, '%')) OR
        LOWER(bt.route.destination.name) LIKE LOWER(CONCAT('%', :route, '%'))
    )
    AND (:status IS NULL OR bt.status = :status)
""")
    List<BusTrip> findFiltered(
            @Param("route") String route,
            @Param("status") BusTripStatus status,
            Sort sort
    );

    @Query("""
    SELECT bt FROM BusTrip bt
    WHERE (
        :route IS NULL OR
        LOWER(bt.route.origin.name) LIKE LOWER(CONCAT('%', :route, '%')) OR
        LOWER(bt.route.destination.name) LIKE LOWER(CONCAT('%', :route, '%'))
    )
    AND (:status IS NULL OR bt.status = :status)
""")
    Page<BusTrip> findFilteredPage(
            @Param("route") String route,
            @Param("status") BusTripStatus status,
            Pageable pageable
    );
}


