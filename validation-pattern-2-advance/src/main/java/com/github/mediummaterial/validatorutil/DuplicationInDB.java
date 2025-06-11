package com.github.mediummaterial.validatorutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicationInDB {
    String message() default "data telah ada";

    String columnName() default "NOT_DEFINED";
}
