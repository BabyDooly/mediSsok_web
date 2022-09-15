package mediSsok.mediSsokspring.dto.medicines;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class MedicinesSearchRequestDto {
    private String name;
    private String companyName;
    private String mark;
    private String line;
    private String formulation;
    private String shape;
    private String color;

    public MedicinesSearchRequestDto(){
        name = "";
        companyName = "";
        mark = "";
        line = "";
        formulation = "";
        shape = "";
        color = "";
    }
}
