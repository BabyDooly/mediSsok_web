package mediSsok.mediSsokspring.domain.repository.medicines;

import mediSsok.mediSsokspring.domain.entity.medicines.Medicines;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicinesRepository extends JpaRepository<Medicines, Long> {
    
    // 알약 검색
    @Query("SELECT m FROM Medicines m WHERE " +
            "m.name LIKE %:name% " +
            "AND m.companyName LIKE %:companyName% " +
            "AND (m.markBack LIKE %:mark% OR m.markFront LIKE %:mark%) " +
            "AND (m.markBack LIKE %:line% OR m.markFront LIKE %:line%) " +
            "AND m.formulation LIKE %:formulation% " +
            "AND m.shape LIKE %:shape% " +
            "AND m.color LIKE %:color%")
    Page<MedicinesResponseDto> searchByMedicines(
            @Param("name") String name,
            @Param("companyName") String companyName,
            @Param("mark") String mark,
            @Param("line") String line,
            @Param("formulation") String formulation,
            @Param("shape") String shape,
            @Param("color") String color,
            Pageable pageable
    );

    // 분할선 '없음' 검색
    @Query("SELECT m FROM Medicines m WHERE " +
            "m.name LIKE %:name% " +
            "AND m.companyName LIKE %:companyName% " +
            "AND (m.markBack LIKE %:mark% OR m.markFront LIKE %:mark%) " +
            "AND m.markBack NOT LIKE %:line% AND m.markFront NOT LIKE %:line% " +
            "AND m.formulation LIKE %:formulation% " +
            "AND m.shape LIKE %:shape% " +
            "AND m.color LIKE %:color%")
    Page<MedicinesResponseDto> searchNotLine(
            @Param("name") String name,
            @Param("companyName") String companyName,
            @Param("mark") String mark,
            @Param("line") String line,
            @Param("formulation") String formulation,
            @Param("shape") String shape,
            @Param("color") String color,
            Pageable pageable
    );

    // 모양 '기타' 검색
    @Query("SELECT m FROM Medicines m WHERE " +
            "m.name LIKE %:name% " +
            "AND m.companyName LIKE %:companyName% " +
            "AND (m.markBack LIKE %:mark% OR m.markFront LIKE %:mark%) " +
            "AND (m.markBack LIKE %:line% OR m.markFront LIKE %:line%) " +
            "AND m.formulation LIKE %:formulation% " +
            "AND m.shape NOT LIKE %:shape1% AND m.shape NOT LIKE %:shape2% AND m.shape NOT LIKE %:shape3% AND m.shape NOT LIKE %:shape4% AND m.shape NOT LIKE %:shape5% " +
            "AND m.shape NOT LIKE %:shape6% AND m.shape NOT LIKE %:shape7% AND m.shape NOT LIKE %:shape8% AND m.shape NOT LIKE %:shape9% AND m.shape NOT LIKE %:shape10% " +
            "AND m.color LIKE %:color%")
    Page<MedicinesResponseDto> searchNotShape(
            @Param("name") String name,
            @Param("companyName") String companyName,
            @Param("mark") String mark,
            @Param("line") String line,
            @Param("formulation") String formulation,
            @Param("shape1") String shape1,
            @Param("shape2") String shape2,
            @Param("shape3") String shape3,
            @Param("shape4") String shape4,
            @Param("shape5") String shape5,
            @Param("shape6") String shape6,
            @Param("shape7") String shape7,
            @Param("shape8") String shape8,
            @Param("shape9") String shape9,
            @Param("shape10") String shape10,
            @Param("color") String color,
            Pageable pageable
    );

    // 모양 '기타', 분할선 '없음' 검색
    @Query("SELECT m FROM Medicines m WHERE " +
            "m.name LIKE %:name% " +
            "AND m.companyName LIKE %:companyName% " +
            "AND (m.markBack LIKE %:mark% OR m.markFront LIKE %:mark%) " +
            "AND m.markBack NOT LIKE %:line% AND m.markFront NOT LIKE %:line% " +
            "AND m.formulation LIKE %:formulation% " +
            "AND m.shape NOT LIKE %:shape1% AND m.shape NOT LIKE %:shape2% AND m.shape NOT LIKE %:shape3% AND m.shape NOT LIKE %:shape4% AND m.shape NOT LIKE %:shape5% " +
            "AND m.shape NOT LIKE %:shape6% AND m.shape NOT LIKE %:shape7% AND m.shape NOT LIKE %:shape8% AND m.shape NOT LIKE %:shape9% AND m.shape NOT LIKE %:shape10% " +
            "AND m.color LIKE %:color%")
    Page<MedicinesResponseDto> searchNotShapeLine(
            @Param("name") String name,
            @Param("companyName") String companyName,
            @Param("mark") String mark,
            @Param("line") String line,
            @Param("formulation") String formulation,
            @Param("shape1") String shape1,
            @Param("shape2") String shape2,
            @Param("shape3") String shape3,
            @Param("shape4") String shape4,
            @Param("shape5") String shape5,
            @Param("shape6") String shape6,
            @Param("shape7") String shape7,
            @Param("shape8") String shape8,
            @Param("shape9") String shape9,
            @Param("shape10") String shape10,
            @Param("color") String color,
            Pageable pageable
    );
}