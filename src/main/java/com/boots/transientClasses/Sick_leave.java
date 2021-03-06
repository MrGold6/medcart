package com.boots.transientClasses;

import com.boots.entity.Disease;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class Sick_leave extends Document {

    private Date start_date;
    private Disease start_disease;
    private String school;
    private String contact;

}
