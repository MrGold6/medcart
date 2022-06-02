package com.boots.transientClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Medicine {

    private String name;
    private String dose;
    private int number_of_day;
    private String instruction;

}
