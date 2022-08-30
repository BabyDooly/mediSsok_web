package mediSsok.mediSsokspring.dto.medicines;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicinesResponseDto {
    private long id;
    private String name;
    private long companyId;
    private String companyName;
    private String imageUrl;
    private String frontMark;
    private String behindMark;
    private String shape;
    private String color;


}