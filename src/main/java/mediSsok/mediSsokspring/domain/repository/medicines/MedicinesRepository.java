package mediSsok.mediSsokspring.domain.repository.medicines;

import mediSsok.mediSsokspring.domain.entity.medicines.Medicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicinesRepository extends JpaRepository<Medicines, Long> {
    @Query("SELECT m FROM Medicines m WHERE " +
            "m.name LIKE %:name% " +
            "AND m.companyName LIKE %:companyName% " +
            "AND ((m.markBack LIKE %:mark1% AND m.markFront LIKE %:mark2%) OR (m.markBack LIKE %:mark2% AND m.markFront LIKE %:mark1%)) " +
            "AND (m.markBack LIKE %:line% OR m.markFront LIKE %:line%) " +
            "AND m.formulation LIKE %:formulation% " +
            "AND m.shape LIKE %:shape% " +
            "AND m.color LIKE %:color%")
    List<Medicines> searchByMedicines(
            @Param("name") String name,
            @Param("companyName") String companyName,
            @Param("mark1") String mark1,
            @Param("mark2") String mark2,
            @Param("line") String line,
            @Param("formulation") String formulation,
            @Param("shape") String shape,
            @Param("color") String color
    );
}