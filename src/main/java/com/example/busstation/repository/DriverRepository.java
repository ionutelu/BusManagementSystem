package com.example.busstation.repository;
import com.example.busstation.model.Driver;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("""
        SELECT d FROM Driver d
        WHERE (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:minExperience IS NULL OR d.experienceYears >= :minExperience)
    """)
    List<Driver> findFiltered(
            @Param("name") String name,
            @Param("minExperience") Integer minExperience,
            Sort sort
    );
}

