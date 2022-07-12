package com.boots.transientClasses;

import com.boots.entity.Department;
import com.boots.entity.Doctor;
import com.boots.entity.Specialization;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static List<Doctor> firstMinusSecondArraysDoctor(List<Doctor> firstArrayList, List<Doctor> secondArrayList) {
        Set<Doctor> firstArray = new HashSet<>(firstArrayList);
        Set<Doctor> secondArray = new HashSet<>(secondArrayList);

        firstArray.removeAll(secondArray);
        return new ArrayList<>(firstArray);
    }

    public static List<Department> firstMinusSecondArraysDepartment(List<Department> firstArrayList, List<Department> secondArrayList) {
        Set<Department> firstArray = new HashSet<>(firstArrayList);
        Set<Department> secondArray = new HashSet<>(secondArrayList);

        firstArray.removeAll(secondArray);
        return new ArrayList<>(firstArray);
    }

    public static List<Specialization> firstMinusSecondArraysSpecialization(List<Specialization> firstArrayList, List<Specialization> secondArrayList) {
        Set<Specialization> firstArray = new HashSet<>(firstArrayList);
        Set<Specialization> secondArray = new HashSet<>(secondArrayList);

        firstArray.removeAll(secondArray);
        return new ArrayList<>(firstArray);
    }

}
