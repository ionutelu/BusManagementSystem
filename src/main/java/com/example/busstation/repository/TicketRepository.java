package com.example.busstation.repository;
import com.example.busstation.model.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
        SELECT t FROM Ticket t
        WHERE (:busTripId IS NULL OR t.busTrip.id = :busTripId)
          AND (:passengerName IS NULL OR LOWER(t.passenger.name) LIKE LOWER(CONCAT('%', :passengerName, '%')))
          AND (:maxPrice IS NULL OR t.price <= :maxPrice)
    """)
    List<Ticket> findFiltered(
            @Param("busTripId") Long busTripId,
            @Param("passengerName") String passengerName,
            @Param("maxPrice") Double maxPrice,
            Sort sort
    );}
