package com.boots.transientClasses;

import com.boots.entity.*;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class Sort {

    public List<User> sortUser(List<User> users, int i) {
        switch (i) {
            case 1: {
                users.sort(Comparator.comparing(User::getUsername));
                break;
            }
            case 2: {
                users.sort(Comparator.comparing(User::isSelected));
                break;
            }

        }

        return users;
    }

    public List<Unit> sortUnit(List<Unit> units, int i) {
        units.sort(Comparator.comparing(Unit::getName));

        return units;
    }

    public List<MedicineCatalog> sortMedicineCatalog(List<MedicineCatalog> medicineCatalog, int i) {
        switch (i) {
            case 1: {
                medicineCatalog.sort(Comparator.comparing(MedicineCatalog::getATX));
                break;
            }
            case 2: {
                medicineCatalog.sort(Comparator.comparing(MedicineCatalog::getName));
                break;
            }

        }

        return medicineCatalog;
    }

    public List<Disease> sortDisease(List<Disease> diseases, int i) {
        switch (i) {
            case 1: {
                diseases.sort(Comparator.comparing(Disease::getICD_10));
                break;
            }
            //sortSpecialization
            case 2: {
                diseases.sort(Comparator.comparing(Disease::getName));
                break;
            }

        }

        return diseases;
    }

    public List<Specialization> sortSpecialization(List<Specialization> specializations, int i) {
        switch (i) {
            case 1: {
                specializations.sort(Comparator.comparing(Specialization::getId));
                break;
            }
            //sortSpecialization
            case 2: {
                specializations.sort(Comparator.comparing(Specialization::getName));
                break;
            }

        }

        return specializations;
    }

    public List<Role> sortRole(List<Role> roles, int i) {
        switch (i) {
            case 1: {
                roles.sort(Comparator.comparing(Role::getId));
                break;
            }
            //sortSpecialization
            case 2: {
                roles.sort(Comparator.comparing(Role::getName));
                break;
            }
        }

        return roles;
    }

    public List<Doctor> sortDoctor(List<Doctor> doctors, int i) {
        switch (i) {
            case 1: {
                doctors.sort(Comparator.comparing(Doctor::getRNTRC));
                break;
            }
            case 2: {
                doctors.sort(Comparator.comparing(Doctor::getSurname));
                break;
            }

            case 3: {
                doctors.sort(Comparator.comparing(Doctor::getSex));
                break;
            }

            case 4: {
                doctors.sort(Comparator.comparing(Doctor::getTelephone_number));
                break;
            }

            case 5: {
                doctors.sort(Comparator.comparing(o -> o.getSpecialization().getName()));
                break;
            }

        }

        return doctors;
    }

    public List<Schedule> sortSchedule(List<Schedule> schedules, int i) {
        switch (i) {
            case 1: {
                schedules.sort(Comparator.comparing(Schedule::getId));
                break;
            }
            case 2: {
                schedules.sort(Comparator.comparing(Schedule::getDayName).thenComparing(Schedule::getTime));
                break;
            }

            case 3: {
                schedules.sort(Comparator.comparing(Schedule::getTime));
                break;
            }
        }

        return schedules;
    }

    public List<Patient> sortPatient(List<Patient> patients, int i) {
        switch (i) {
            case 1: {
                patients.sort(Comparator.comparing(Patient::getRNTRC));
                break;
            }
            case 2: {
                patients.sort(Comparator.comparing(Patient::getSurname));
                break;
            }

            case 3: {
                patients.sort(Comparator.comparing(Patient::getSex));
                break;
            }

            case 4: {
                patients.sort(Comparator.comparing(Patient::getTelephone_number));
                break;
            }

        }

        return patients;
    }

    public List<Visit> sortVisit(List<Visit> visits, int i) {
        switch (i) {
            case 1: {
                visits.sort(Comparator.comparing(Visit::getNumber));
                break;
            }
            case 2: {
                visits.sort(Comparator.comparing(Visit::getDate));
                break;
            }

            case 3: {
                visits.sort(Comparator.comparing(o -> o.getDisease().getName()));
                break;
            }

            case 4: {
                visits.sort(Comparator.comparing(o -> o.getDoctor().getSpecialization().getName()));
                break;
            }

            case 5: {
                visits.sort(Comparator.comparing(o -> o.getSchedule().getTime()));
                break;
            }

            case 6: {
                visits.sort(Comparator.comparing(Visit::getStatus));
                break;
            }

        }

        return visits;
    }

    public List<Direction> sortDirection(List<Direction> directions, int i) {
        switch (i) {
            case 1: {
                directions.sort(Comparator.comparing(Direction::getNumber));
                break;
            }
            case 2: {
                directions.sort(Comparator.comparing(o -> o.getSpecialization().getName()));
                break;
            }

            case 3: {
                directions.sort(Comparator.comparing(Direction::getStatus));
                break;
            }

        }

        return directions;
    }

}
