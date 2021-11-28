package com.boots.transientClasses;
import com.boots.entity.Disease;
import com.boots.transientClasses.Document;

import java.sql.Date;
//потібно вираховувати вік пацієнта
public class Sick_leave extends Document {

    private Date start_date;
    private Disease start_disease;
    private String school;
    private String contact;

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Disease getStart_disease() {
        return start_disease;
    }

    public void setStart_disease(Disease start_disease) {
        this.start_disease = start_disease;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
