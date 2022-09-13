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
        String name = entity.getName();
        String codeName = entity.getCodeName();
        String SB = entity.getSB();
        int enter;

        if (name.contains("(")){
            enter = name.indexOf("(");
            name = name.substring(0,enter) + "<br>" + name.substring(enter);
        }

        if (codeName.contains("(")){
            enter = codeName.indexOf("(");
            codeName = codeName.substring(0,enter) + "<br>" + codeName.substring(enter);
        }

        if (SB.contains("의약품")){
            enter = SB.indexOf("의약품");
            SB = SB.substring(0,enter);
        }


        this.id = entity.getId();
        this.name = name;
        this.companyName = entity.getCompanyName();
        this.imageUrl = entity.getImageUrl();
        this.formulation = entity.getFormulation();
        this.codeName = codeName;
        this.SB = SB;
    }
}