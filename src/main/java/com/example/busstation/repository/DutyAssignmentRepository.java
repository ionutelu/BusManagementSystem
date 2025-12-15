package com.example.busstation.repository;
import com.example.busstation.model.DriverRole;
import com.example.busstation.model.DutyAssignment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyAssignmentRepository extends JpaRepository<DutyAssignment, Long> {

    @Query("""
    SELECT da FROM DutyAssignment da
    WHERE (:tripId IS NULL OR da.busTrip.id = :tripId)
      AND (:staffName IS NULL OR LOWER(da.staff.name) LIKE LOWER(CONCAT('%', :staffName, '%')))
      AND (:role IS NULL OR da.role = :role)
""")
    List<DutyAssignment> findFiltered(
            @Param("tripId") Long tripId,
            @Param("staffName") String staffName,
            @Param("role") DriverRole role,
            Sort sort
    );
}
