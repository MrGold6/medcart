package com.boots.transientClasses;

import com.boots.entity.Disease;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseStatistic {
    Disease disease;
    Long count;
}
