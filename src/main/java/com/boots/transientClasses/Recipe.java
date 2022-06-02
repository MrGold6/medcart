package com.boots.transientClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Recipe extends Document {

    private List<Medicine> medicineList = new ArrayList<>();

    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);
    }
}
