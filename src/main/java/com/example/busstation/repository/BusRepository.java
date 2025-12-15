package com.example.busstation.repository;
import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

        @Query("""
        SELECT b FROM Bus b
        WHERE (:vin IS NULL OR b.vin LIKE %:vin%)
          AND (:status IS NULL OR b.status = :status)
          AND (:minCapacity IS NULL OR b.capacity >= :minCapacity)
    """)
        List<Bus> findFiltered(
                @Param("vin") String vin,
                @Param("status") BusStatus status,
                @Param("minCapacity") Integer minCapacity,
                Sort sort
        );
}
