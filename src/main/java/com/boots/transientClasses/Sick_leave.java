package com.boots.transientClasses;

import com.boots.entity.Disease;
import com.boots.transientClasses.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

//TODO: потібно вираховувати вік пацієнта
@NoArgsConstructor
@Getter
@Setter
public class Sick_leave extends Document {

    private Date start_date;
    private Disease start_disease;
    private String school;
    private String contact;

}
