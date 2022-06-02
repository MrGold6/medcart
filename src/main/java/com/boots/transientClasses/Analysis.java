package com.boots.transientClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Calendar;

@NoArgsConstructor
@Getter
@Setter
public class Analysis extends Document {

    private int number;
    private Date date;
    private String laboratory;
    private String target;
    private String disease;

    public String getDateToString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + "." + month + "." + year;
    }
}
