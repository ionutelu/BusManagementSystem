package com.example.busstation.repository;
import com.example.busstation.model.BusStation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {

    @Query("""
        SELECT bs FROM BusStation bs
        WHERE (:name IS NULL OR LOWER(bs.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:damaged IS NULL OR bs.isDamaged = :damaged)
    """)
    List<BusStation> findFiltered(
            @Param("name") String name,
            @Param("damaged") Boolean damaged,
            Sort sort
    );
}
