package com.example.busstation.repository;
import com.example.busstation.model.Passenger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {


    @Query("""
        SELECT p FROM Passenger p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:currency IS NULL OR p.currency = :currency)
    """)
    List<Passenger> findFiltered(
            @Param("name") String name,
            @Param("currency") String currency,
            Sort sort
    );}
