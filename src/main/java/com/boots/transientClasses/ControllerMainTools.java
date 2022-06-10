package com.boots.transientClasses;

import com.boots.entity.Doctor;
import com.boots.entity.Patient;
import com.boots.entity.User;
import com.boots.entity.Visit;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

public class ControllerMainTools {


    public static String dateToString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + "." + month + "." + year;
    }

    public static Long getIdPatientSplit(String id_visit) {
        String[] words = id_visit.split("_");
        return Long.parseLong(words[1]);
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        return new java.sql.Date(currentDate.getTime());
    }
}
