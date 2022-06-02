package com.boots.transientClasses;

import com.boots.entity.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class Document {

    private Visit visit;

}
