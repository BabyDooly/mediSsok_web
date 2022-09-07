package mediSsok.mediSsokspring.dto.medicines;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.medicines.Medicines;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicinesResponseDto {
    private Long id;
    private String name;
    private String companyName;
    private String imageUrl;
    private String formulation;
    private String codeName;
    private String SB;

    public MedicinesResponseDto(Medicines entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.companyName = entity.getCompanyName();
        this.imageUrl = entity.getImageUrl();
        this.formulation = entity.getFormulation();
        this.codeName = entity.getCodeName();
        this.SB = entity.getSB();
    }
}