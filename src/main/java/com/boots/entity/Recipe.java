package com.boots.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Recipe extends Document{

    private List<Medicine> medicineList = new ArrayList<>();

    public Recipe()
    {

    }
    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    public void addMedicine(Medicine medicine)
    {
        medicineList.add(medicine);
    }
}
