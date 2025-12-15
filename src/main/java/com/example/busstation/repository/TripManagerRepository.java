package com.example.busstation.repository;
import com.example.busstation.model.TripManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripManagerRepository extends JpaRepository<TripManager, Long> {

    @Query("""
        SELECT tm FROM TripManager tm
        WHERE (:name IS NULL OR LOWER(tm.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:email IS NULL OR LOWER(tm.email) LIKE LOWER(CONCAT('%', :email, '%')))
          AND (:employeeCode IS NULL OR LOWER(tm.employeeCode) LIKE LOWER(CONCAT('%', :employeeCode, '%')))
    """)
    List<TripManager> findFiltered(
            @Param("name") String name,
            @Param("email") String email,
            @Param("employeeCode") String employeeCode,
            Sort sort
    );}
