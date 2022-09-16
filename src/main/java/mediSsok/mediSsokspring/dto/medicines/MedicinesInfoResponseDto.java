package mediSsok.mediSsokspring.dto.medicines;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicines.Medicines;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicinesInfoResponseDto {
    // 성상
    private String appearance;
    // 성분정보
    private List<String> ingredient;
    // 저장방법
    private String save;
    // 효능효과
    private String effect;
    // 용법용량
    private String intake;
}